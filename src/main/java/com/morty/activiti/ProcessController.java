package com.morty.activiti;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.common.entity.Result;
import com.common.util.BasicUtil;
import com.common.util.UserUtil;
import com.morty.entity.ManagerEntity;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


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
    protected IdentityService identityService;


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
     *      startUnitId: 发起人单位id
     */
    @GetMapping("run/{key}")
    @ResponseBody
    public Result run(@PathVariable String key,HttpServletRequest request){
        //设置当前用户
        ManagerEntity manager = UserUtil.getManagerSession(request.getSession());
        identityService.setAuthenticatedUserId(String.valueOf(manager.getManagerId()));

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, BasicUtil.assemblyRequestMap());

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
}
