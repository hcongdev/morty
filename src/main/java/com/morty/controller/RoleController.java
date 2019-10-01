package com.morty.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.common.entity.Result;
import com.morty.entity.RoleEntity;
import com.morty.service.RoleMenuService;
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

    @Autowired
    public RoleMenuService roleMenuService;

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

    @PostMapping("/del")
    @ResponseBody
    public Result del(@RequestBody(required = false) int[] roleIds){
        if (roleIds.length <= 0){
            return Result.failure("删除编号不能为空");
        }
        roleService.delRole(roleIds);
        return Result.success();
    }

    //保存
    @PostMapping("/update")
    @ResponseBody
    public Result update(@ModelAttribute RoleEntity role){
        if (StringUtils.isEmpty(role.getRoleName())){
            return Result.failure("角色名称不能为空");
        }
        roleService.updateRole(role);

        return Result.success();
    }

    /**
     * 获取角色详情
     * @param role
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    public Result get(@ModelAttribute RoleEntity role){
        RoleEntity roleEntity = roleService.getEntity(role);

        //查询角色对应的菜单
        List<Integer> menuIdList = roleMenuService.queryMenuIdList(roleEntity.getRoleId());

        roleEntity.setMenuIdList(menuIdList);
        return Result.success(roleEntity);
    }
}
