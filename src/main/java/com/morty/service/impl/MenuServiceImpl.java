package com.morty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.morty.entity.MenuEntity;
import com.morty.mapper.MenuMapper;
import com.morty.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuEntity> list(MenuEntity menuEntity) {
        return menuMapper.list(menuEntity);
    }

    @Override
    public void deleteIds(int[] ids) {
        this.getBaseMapper().delete(ids);
    }

    @Override
    public MenuEntity getEntity(MenuEntity menu) {
        return menuMapper.getEntity(menu);
    }
}
