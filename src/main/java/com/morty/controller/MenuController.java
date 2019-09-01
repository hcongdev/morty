package com.morty.controller;

import com.common.entity.Result;
import com.common.exception.MyException;
import com.morty.constant.Constant;
import com.morty.entity.MenuEntity;
import com.morty.service.MenuService;
import org.apache.commons.lang.StringUtils;
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

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    @PostMapping("/save")
    public Result save(@ModelAttribute MenuEntity menu){
        verifyForm(menu);
        menuService.save(menu);
        return Result.success();
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @PostMapping("/update")
    public Result update(@ModelAttribute MenuEntity menu){
        verifyForm(menu);
        menuService.updateById(menu);
        return Result.success();
    }

    @GetMapping("/del")
    public Result del(@ModelAttribute MenuEntity menu){

        return Result.success();
    }

    /**
     * 校验参数
     */
    private void verifyForm(MenuEntity menu){
        if (StringUtils.isEmpty(menu.getMenuName())){
           throw  new MyException("菜单名称不能为空");
        }

        if(menu.getMenuParentId() == null){
            throw new MyException("上级菜单不能为空");
        }
        //菜单
        if(menu.getMenuType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getMenuUrl())){
                throw new MyException("菜单URL不能为空");
            }
        }
        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getMenuParentId() != 0){
            MenuEntity parentMenu = menuService.getById(menu.getMenuParentId());
            parentType = parentMenu.getMenuType();
        }

        //目录、菜单
        if(menu.getMenuType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getMenuType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new MyException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getMenuType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new MyException("上级菜单只能为菜单类型");
            }
            return ;
        }

    }
}
