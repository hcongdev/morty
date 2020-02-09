package com.morty.activiti;

import com.common.entity.Result;
import com.common.util.UserUtil;
import com.morty.entity.ManagerEntity;
import com.morty.service.ManagerService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史管理
 *
 */
@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private ManagerService managerService;

    @RequestMapping("index")
    public String index(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        return "activiti/history/index";
    }

    /**
     * 我发起的记录
     *
     * @return
     */
    @GetMapping(value = "/process/mys")
    @ResponseBody
    public Result myProcessStarted(HttpServletResponse response, HttpServletRequest request) {
        ManagerEntity manger = UserUtil.getManagerSession(request.getSession());
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().startedBy(String.valueOf(manger.getManagerId())).list();
        List historyList = new ArrayList();
        Map<String, Object> stringObjectMap = new HashMap<>();
        for (int i =0;i<historicProcessInstanceList.size();i++){
            stringObjectMap = packeyVariables(historicProcessInstanceList.get(i).getId());
            stringObjectMap.put("name",historicProcessInstanceList.get(i).getName());
            stringObjectMap.put("id",historicProcessInstanceList.get(i).getId());
            stringObjectMap.put("processInstanceId",historicProcessInstanceList.get(i).getId());
            ManagerEntity managerEntity = managerService.getById(historicProcessInstanceList.get(i).getStartUserId());
            stringObjectMap.put("startUser",managerEntity.getManagerName());
            historyList.add(stringObjectMap);
        }
        return Result.success(historyList);
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
     * 删除历史记录
     * @param processInstanceId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result deleteOne(@RequestParam("processInstanceId")String processInstanceId){
        historyService.deleteHistoricProcessInstance(processInstanceId);
        return Result.success();
    }
}
