package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.MenuEntity;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuEntity> {
    List<MenuEntity> list(MenuEntity menuEntity);

    void delete(int[] ids);

    MenuEntity getEntity(MenuEntity menu);
}
