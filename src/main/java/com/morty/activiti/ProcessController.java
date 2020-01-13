package com.morty.activiti;

import cn.hutool.json.JSONUtil;
import com.common.entity.Result;
import com.common.util.BasicUtil;
import com.common.util.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morty.entity.ManagerEntity;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 流程管理
 */
@Slf4j
@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;




    @RequestMapping("index")
    public String index(HttpServletResponse response, HttpServletRequest request) {
        return "activiti/process/index";
    }


    /**
     * 获取所有流程
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Result processList(){
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
       //这里直接返回processDefinitionList 会出现Direct self-reference leading to cycle 错误
        //所以先转json再转数组
        // TODO: 待解决
        return Result.success(JSONUtil.parseArray(JSONUtil.toJsonStr(processDefinitionList)));
}

    /**
     * 启动流程
     *
     *  key:  流程定义KEY
     *      bussId：  业务对象序号
     *      bussName：业务对象名称
     *      bussType：业务对象类型
     *      startUserId: 发起人id
     */
    @GetMapping("run/{key}")
    @ResponseBody
    public Result run(@PathVariable String key,HttpServletRequest request){
        //设置当前用户
        ManagerEntity manager = UserUtil.getManagerSession(request.getSession());
        identityService.setAuthenticatedUserId(String.valueOf(manager.getManagerId()));
        Map<String, Object> variables =BasicUtil.assemblyRequestMap();
        variables.put("managerId",String.valueOf(manager.getManagerId()));
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, variables);

        log.info("启动一个流程实例，id为：{}",processInstance.getId());
        return Result.success(processInstance.getId());
    }

    /**
     * 删除流程
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("deploymentId")String deploymentId){
        repositoryService.deleteDeployment(deploymentId,true);
        return Result.success();
    }

    /**
     * 读取资源流程
     */
    @RequestMapping("/read-resource")
    public void readResource(@RequestParam("id")String id, @RequestParam("resourceName")String resourceName, HttpServletResponse response) throws IOException {
        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition pd = pdq.processDefinitionId(id).singleResult();
        //通过接口读取资源流
        InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(),resourceName);
        //输出资源内容到相应的对象
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b,0,1024)) != -1){
            response.getOutputStream().write(b,0,len);
        }
    }


    /**
     * 导出model的图片，静态流程图
     */
    //@Auth(verifyLogin=false)
    @RequestMapping(value = "/exportPNG", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    //@ResponseBody
    public void exportPNG(@RequestParam("modelId") String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
            InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel,
                    "png",
                    Collections.<String>emptyList(), Collections.<String>emptyList(),
                    "WenQuanYi Micro Hei", "WenQuanYi Micro Hei","WenQuanYi Micro Hei",
                    null, 1.0);

            OutputStream out = response.getOutputStream();
            for (int b = -1; (b = inputStream.read()) != -1; ) {
                out.write(b);
            }
            out.close();
            inputStream.close();

        } catch (Exception e) {
            log.info("导出失败：modelId="+modelId, e);
        }

    }
}
