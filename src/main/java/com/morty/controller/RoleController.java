package com.morty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RoleController {

    @GetMapping("/role")
    public String role(){
        return "role/role";
    }
}
