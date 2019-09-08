package com.morty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.morty.entity.ManagerEntity;
import com.morty.entity.MenuEntity;

import java.util.List;

public interface MenuService extends IService<MenuEntity> {

    /**
     * 菜单列表
     * @param MenuEntity
     * @return
     */
    List<MenuEntity> list(MenuEntity MenuEntity);


    /**
     * 批量删除
     * @param ids
     */
    void deleteIds(int[] ids);

    /**
     * 获取菜单信息
     * @param menu
     */
    MenuEntity getEntity(MenuEntity menu);
}
