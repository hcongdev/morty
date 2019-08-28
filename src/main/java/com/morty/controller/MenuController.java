package com.morty.controller;

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
     * 查询菜单列表
     * @return
     */
    @RequestMapping("/list")
    public List<MenuEntity> list(){
        List<MenuEntity> list = menuService.list();
        return list;
    }

    @RequestMapping("/save")
    public Result save(@ModelAttribute MenuEntity menu){
        menuService.save(menu);
        return Result.success();
    }
}
