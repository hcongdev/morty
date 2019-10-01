package com.morty.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.common.entity.Result;
import com.morty.entity.ManagerEntity;
import com.morty.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * 用户列表页面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "/manager/index";
    }

    /**
     * 用户表单页面
     * @return
     */
    @GetMapping("/form")
    public String form(){
        return "/manager/form";
    }


    @GetMapping("/list")
    @ResponseBody
    public Result list(@ModelAttribute ManagerEntity managerEntity){
        List<ManagerEntity> list = managerService.queryByEntity(managerEntity);
        return Result.success(list);
    }

    @PostMapping("/save")
    @ResponseBody
    public Result save(@ModelAttribute ManagerEntity managerEntity){
        verifyForm(managerEntity);
        managerEntity.setManagerPassword(SecureUtil.md5(managerEntity.getManagerPassword()));
        managerService.save(managerEntity);
        return Result.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@ModelAttribute ManagerEntity managerEntity){
        verifyForm(managerEntity);
        managerService.updateById(managerEntity);
        return Result.success();
    }

    @GetMapping("/get")
    @ResponseBody
    public Result get(@ModelAttribute ManagerEntity managerEntity){
        ManagerEntity manager = managerService.getEntity(managerEntity);
        return Result.success(manager);
    }
    /**
     * 校验参数
     */
    private Result verifyForm(ManagerEntity managerEntity){
        if (StrUtil.isBlank(managerEntity.getManagerName())){
            return Result.failure("用户名不能为空");
        }
        if (StrUtil.isBlank(managerEntity.getManagerPassword())){
            return Result.failure("用户密码不能为空");
        }
        if (StrUtil.isBlank(managerEntity.getManagerNickname())){
            return Result.failure("用户昵称不能为空");
        }
        return Result.success();

    }
}
