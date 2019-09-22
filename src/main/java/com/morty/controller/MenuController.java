package com.morty.controller;

import com.common.entity.Result;
import com.morty.constant.Constant;
import com.morty.entity.MenuEntity;
import com.morty.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * 查询菜单列表，children
     * @return
     */
    @RequestMapping("/menuList")
    @ResponseBody
    public List<MenuEntity> menuList(){
        List<MenuEntity> menuList = menuService.list();
        MenuEntity menu = new MenuEntity();
        List<MenuEntity> _menuList= new ArrayList<>();
        for (int i=0;i<menuList.size();i++){
            menu = menuList.get(i);
            menu.setChildren(getChildrenList(menuList.get(i).getMenuId(),menuList));
            if ( menuList.get(i).getMenuParentId() == 0){
                _menuList.add(menu);
            }
        }
        return _menuList;
    }

    private List<MenuEntity> getChildrenList(int menuId,List<MenuEntity> list) {
        List<MenuEntity> children = new ArrayList<>();
        for (MenuEntity menu : list){
            if (menu.getMenuParentId() == menuId){
                children.add(menu);
            }
        }
        return children;
    }


    /**
     * 保存菜单
     * @param menu
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
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
    @ResponseBody
    public Result update(@ModelAttribute MenuEntity menu){
        verifyForm(menu);
        menuService.updateById(menu);
        return Result.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public Result del(@RequestBody List<MenuEntity> menus){
        int[] ids = new int[menus.size()];
        for(int i = 0;i<menus.size();i++){
            ids[i] =menus.get(i).getMenuId();
        }
        menuService.deleteIds(ids);
        return Result.success();
    }

    /**
     * 根据用户编号获取菜单信息
     * @param menu
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    public Result get(@ModelAttribute MenuEntity menu){
        MenuEntity menuEntity = menuService.getEntity(menu);
        return Result.success(menuEntity);
    }
    /**
     * 校验参数
     */
    private Result verifyForm(MenuEntity menu){
        if (StringUtils.isEmpty(menu.getMenuName())){
          return Result.failure("菜单名称不能为空");
        }

        if(menu.getMenuParentId() == null){
            return Result.failure("上级菜单不能为空");
        }
        //菜单
        if(menu.getMenuType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getMenuUrl())){
                return Result.failure("菜单URL不能为空");
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
                return Result.failure("上级菜单只能为目录类型");
            }
        }

        //按钮
        if(menu.getMenuType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                return Result.failure("上级菜单只能为菜单类型");
            }
        }
        return Result.success();
    }
}
