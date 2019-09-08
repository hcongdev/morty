package com.morty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.morty.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<MenuEntity> {
    List<MenuEntity> list(MenuEntity menuEntity);

    void delete(int[] ids);

    MenuEntity getEntity(MenuEntity menu);
}
