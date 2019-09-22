package com.morty.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.common.entity.Result;
import com.morty.entity.RoleEntity;
import com.morty.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    public RoleService roleService;

    @GetMapping("/index")
    public String index(){
        return "role/index";
    }


    @GetMapping("/form")
    public String form(){
        return "role/form";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result list(@ModelAttribute RoleEntity roleEntity){
        List<RoleEntity> list = roleService.queryByEntity(roleEntity);
        return Result.success(list);
    }

    //保存
    @PostMapping("/save")
    @ResponseBody
    public Result save(@ModelAttribute RoleEntity role){
        if (StringUtils.isEmpty(role.getRoleName())){
            return Result.failure("角色名称不能为空");
        }
        roleService.saveRole(role);

        return Result.success();
    }

}
