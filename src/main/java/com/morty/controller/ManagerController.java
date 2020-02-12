package com.morty.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.common.entity.Result;
import com.common.util.UserUtil;
import com.morty.entity.ManagerEntity;
import com.morty.service.ManagerRoleService;
import com.morty.service.ManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerRoleService managerRoleService;

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
        managerService.saveManager(managerEntity);
        return Result.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@ModelAttribute ManagerEntity managerEntity){
        verifyForm(managerEntity);
        managerService.updateManager(managerEntity);
        return Result.success();
    }

    @GetMapping("/get")
    @ResponseBody
    public Result get(@ModelAttribute ManagerEntity managerEntity){
        ManagerEntity manager = managerService.getEntity(managerEntity);
        //获取用户角色列表
       List<Integer> roleList = managerRoleService.queryRoleIdList(manager.getManagerId());
       manager.setRoleIdList(roleList);
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

    /**
     * 退出登录
     */
    @GetMapping("/quit")
    @ResponseBody
    public Result quit(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success();
    }

    /**
     * 获取登录用户信息
     */
    @GetMapping("/info")
    @ResponseBody
    public Result info(HttpServletRequest request){
        ManagerEntity _manager = UserUtil.getManagerSession(request.getSession());
        return Result.success(_manager);
    }

    /**
     * 退出登录
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public Result changePassword(@ModelAttribute ManagerEntity managerEntity, HttpServletRequest request){
        if (StrUtil.isBlank(managerEntity.getManagerPassword())){
            return Result.failure("用户密码不能为空");
        }
        if (StrUtil.length(managerEntity.getManagerPassword()) < 4 || StrUtil.length(managerEntity.getManagerPassword()) > 30){
            return Result.failure("用户密码长度在6-30之间");
        }
        // 验证新密码的合法：空格字符
        if (managerEntity.getManagerPassword().contains(" ")) {
            return Result.failure("用户密码长度不能包含空格");
        }
        ManagerEntity _manager = UserUtil.getManagerSession(request.getSession());
        // 将用户输入的原始密码用MD5加密再和数据库中的进行比对
        if ( !_manager.getManagerPassword().equals(SecureUtil.md5(managerEntity.getManagerOldPassword()))){
            return Result.failure("用户密码错误");
        }
        _manager.setManagerPassword(managerEntity.getManagerPassword());
        managerService.updateManager(_manager);
        return Result.success();
    }
}
