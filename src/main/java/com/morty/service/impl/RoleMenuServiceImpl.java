package com.morty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.morty.entity.RoleEntity;
import com.morty.entity.RoleMenuEntity;
import com.morty.mapper.RoleMapper;
import com.morty.mapper.RoleMenuMapper;
import com.morty.service.RoleMenuService;
import com.morty.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuEntity> implements RoleMenuService{

    @Override
    public void saveOrUpdate(int roleId, List<Integer> menuIdList) {
        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for(int menuId : menuIdList){
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setMenuId(menuId);
            roleMenuEntity.setRoleId(roleId);

            this.save(roleMenuEntity);
        }
    }

    @Override
    public List<Long> queryMenuIdList(int roleId) {
        return null;
    }

    @Override
    public int deleteBatch(int[] roleIds) {
        return 0;
    }
}
