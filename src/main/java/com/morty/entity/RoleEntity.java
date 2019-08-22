package com.morty.entity;

public class RoleEntity {
    /**
     * 角色编号
     */
    private int roleId;

    /**
     * 角色编号
     */
    private String roleName;

    /**
     * 管理员编号
     */
    private String managerId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
