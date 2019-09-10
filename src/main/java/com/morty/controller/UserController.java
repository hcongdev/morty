package com.morty.controller;

import com.morty.entity.ManagerEntity;
import com.morty.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ManagerService managerService;

    /**
     * 用户列表页面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "/user/index";
    }

    /**
     * 用户表单页面
     * @return
     */
    @GetMapping("/form")
    public String form(){
        return "/user/form";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<ManagerEntity> list(@ModelAttribute ManagerEntity managerEntity, Model model){
        List<ManagerEntity> list = managerService.list(managerEntity);
        model.addAttribute("list",list);
        return list;
    }
}
