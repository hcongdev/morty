package com.common.util;

import com.morty.entity.ManagerEntity;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpSession;

/**
 * 用户工具类
 */
public class UserUtil {

    public static final String MANAGER = "manager";


    /**
     * 设置用户到session
     *
     * @param session
     * @param manager
     */
    public static void saveUserToSession(HttpSession session, ManagerEntity manager) {
        session.setAttribute(MANAGER, manager);
    }

    /**
     * 从Session获取当前用户信息
     *
     * @param session
     * @return
     */
    public static ManagerEntity getUserSession(HttpSession session) {
        Object attribute = session.getAttribute(MANAGER);
        return attribute == null ? null : (ManagerEntity) attribute;
    }
    /**
     * 从shiro获取当前管理员
     *
     * @param session
     * @return
     */
    public static ManagerEntity getManagerSession(HttpSession session) {
        ManagerEntity attribute = (ManagerEntity) SecurityUtils.getSubject().getPrincipal();
        return attribute == null ? null : (ManagerEntity) attribute;
    }
}
