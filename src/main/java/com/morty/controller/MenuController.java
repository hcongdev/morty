package com.morty.controller;

import cn.hutool.core.util.StrUtil;
import com.common.entity.Result;
import com.morty.entity.MenuEntity;
import com.morty.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 菜单管理页面
     */
    @RequestMapping("/index")
    public String index(){
        return "/menu/index";
    }

    /**
     * 菜单编辑页面
     */
    @RequestMapping("/form")
    public String form(){
        return "/menu/form";
    }
    /**
     * 查询菜单列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<MenuEntity> list(){
        List<MenuEntity> list = menuService.list();
        return list;
    }

    @ResponseBody
    @PostMapping("/save")
    public Result save(@ModelAttribute MenuEntity menu){
        if (StrUtil.isEmpty(menu.getMenuName())){
            return Result.failure("错误");
        }
        menuService.save(menu);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@ModelAttribute MenuEntity menu){
       // menuService.update(menu);
        return Result.success();
    }
}
