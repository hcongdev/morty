package com.morty.shiro;

import cn.hutool.core.util.ObjectUtil;
import com.morty.constant.Constant;
import com.morty.entity.ManagerEntity;
import com.morty.entity.MenuEntity;
import com.morty.mapper.ManagerMapper;
import com.morty.mapper.MenuMapper;
import com.morty.service.ManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private ManagerService managerService;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 身份验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查询是否有此用户
        ManagerEntity newEntity = new ManagerEntity();
        newEntity.setManagerName(token.getUsername());
        ManagerEntity manager = managerService.getEntity(newEntity);
        if (manager == null){
            //账号不存在
            throw new UnknownAccountException("账号或密码不正确");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(manager, manager.getManagerPassword(), this.getName());
        return info;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ManagerEntity manager = (ManagerEntity) principalCollection.getPrimaryPrincipal();
        int managerId = manager.getManagerId();
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(managerId == Constant.SUPER_ADMIN){
            List<MenuEntity> menuList = menuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(MenuEntity menu : menuList){
                permsList.add(menu.getMenuPerms());
            }
        }else{
            permsList = managerMapper.queryAllPerms(managerId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }


}
