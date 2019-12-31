package com.morty.activiti;

import com.common.entity.Result;
import com.common.util.UserUtil;
import com.morty.entity.ManagerEntity;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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




    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
    /**
     * 待办任务
     */
    @GetMapping("/todoTask")
    @ResponseBody
    public Result todoTask(HttpServletRequest request){
        ManagerEntity manager = UserUtil.getManagerSession(request.getSession());
        List<Task> allTask = taskService.createTaskQuery().taskCandidateOrAssigned(String.valueOf(manager.getManagerId())).list();
        return Result.success(allTask);
    }

    /**
     * 待办任务--Portlet
     */
    @RequestMapping(value = "/task/todo/list")
    @ResponseBody
    public List<Map<String, Object>> todoList(HttpSession session) throws Exception {
        ManagerEntity manager = UserUtil.getManagerSession(session);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        // 已经签收的任务
        List<Task> todoList = taskService.createTaskQuery().taskAssignee(String.valueOf(manager.getManagerId())).active().list();
        for (Task task : todoList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "todo");
            result.add(singleTask);
        }

        // 等待签收的任务
        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(String.valueOf(manager.getManagerId())).active().list();
        for (Task task : toClaimList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "claim");
            result.add(singleTask);
        }

        return result;
    }


    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }

    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("createTime", sdf.format(task.getCreateTime()));
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdversion", processDefinition.getVersion());
        singleTask.put("pid", task.getProcessInstanceId());
        return singleTask;
    }

//    @GetMapping("/myTask")
//    @RequestBody
//    public void myTask(){
//        List<Map<String,Object>> list = new ArrayList<>();
//        List<Map<String,Object>> tasks = runtimeInfoService.myTasks(userId);
//        if (!CollectionUtils.isEmpty(tasks)){
//            for (Map<String,Object> task:tasks){
//                Map<String, Object> variables = taskService.getVariables((String) task.get("ID_"));
//                task.putAll(variables);
//                list.add(task);
//            }
//        }
//        return list;
//    }
}
