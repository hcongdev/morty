package com.morty.activiti;

import cn.hutool.json.JSONUtil;
import com.common.entity.Result;
import com.common.util.BasicUtil;
import com.common.util.UserUtil;
import com.morty.entity.ManagerEntity;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务管理
 */
@Slf4j
@Controller
@RequestMapping("/task")
public class TaskAction {

    @Autowired
    private TaskService taskService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;


    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

    /**
     * 任务主页面
     */
    @RequestMapping("index")
    public String index(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        return "activiti/task/index";
    }


    /**
     * 待办任务
     */
    @GetMapping("/todoTask")
    @ResponseBody
    public Result todoTask(HttpServletRequest request){
        ManagerEntity manager = UserUtil.getManagerSession(request.getSession());
        List<Task> allTask = taskService.createTaskQuery().taskCandidateOrAssigned(String.valueOf(manager.getManagerId())).list();
        List tasks = new ArrayList();
        Map<String, Object> stringObjectMap = new HashMap<>();
        for (int i =0;i<allTask.size();i++){
             stringObjectMap = packeyVariables(allTask.get(i).getProcessInstanceId());
            stringObjectMap.put("name",allTask.get(i).getName());
            stringObjectMap.put("createTime",allTask.get(i).getCreateTime());
            stringObjectMap.put("assignee",allTask.get(i).getAssignee());
            stringObjectMap.put("id",allTask.get(i).getId());
            System.out.println(stringObjectMap);
            tasks.add(stringObjectMap);
        }
        return Result.success(JSONUtil.parseArray(JSONUtil.toJsonStr(tasks)));
    }

    //读取历史变量
    private Map<String,Object> packeyVariables(String processInstanceId){
        Map<String,Object> historyVariables = new HashMap<String,Object>();
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()                                      //创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricVariableInstance historicVariableInstance : list){
            historyVariables.put(historicVariableInstance.getVariableName(),historicVariableInstance.getValue());
        }
        return historyVariables;
    }


    /**
     * 读取带跟踪的图片  动态流程图
     */
    @GetMapping(value = "/getpng",  produces = "application/json;charset=utf-8")
    public void getpng(@RequestParam("processInstanceId") String processInstanceId, HttpServletResponse response) throws Exception {

        //获取历史流程实例
        HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);

        for(HistoricActivityInstance tempActivity : highLightedActivitList){
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

        //中文显示的是口口口，设置字体就好了
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",null,1.0);
        //单独返回流程图，不高亮显示
//	        InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }

    }
    /**
     * 获取需要高亮的线
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances){
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }

    @GetMapping("/complete/{taskId}")
    @ResponseBody
    public Result myTask(@PathVariable("taskId")  String taskId, HttpServletRequest request){
        ManagerEntity managerEntity = (ManagerEntity)UserUtil.getManagerSession(request.getSession());
        Map<String, Object> variables = BasicUtil.assemblyRequestMap();
        variables.put("userName",managerEntity.getManagerName());
        taskService.complete(taskId,variables);
        return Result.success();
    }
}
