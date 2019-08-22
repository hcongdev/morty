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
@RequestMapping("/")
public class UserController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    @GetMapping("/list")
    @ResponseBody
    public List<ManagerEntity> list(@ModelAttribute ManagerEntity managerEntity, Model model){
        List<ManagerEntity> list = managerService.list(managerEntity);
        model.addAttribute("list",list);
        return list;
    }
}
