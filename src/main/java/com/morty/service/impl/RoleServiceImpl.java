package com.morty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.morty.entity.RoleEntity;
import com.morty.mapper.RoleMapper;
import com.morty.service.RoleMenuService;
import com.morty.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<RoleEntity> queryByEntity(RoleEntity roleEntity) {
        return roleMapper.queryByEntity(roleEntity);
    }

    /**
     * 保存角色
     * @param role
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(RoleEntity role) {
        roleMapper.save(role);
        //保存角色与菜单关系
        roleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }
}
