package com.morty.activiti;

import com.common.entity.Result;
import com.common.util.UserUtil;
import com.morty.entity.ManagerEntity;
import org.activiti.engine.HistoryService;
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
        return Result.success(historicProcessInstanceList);
    }

}
