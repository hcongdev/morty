package com.morty.entity;

import java.util.Date;

/**
 * 管理员实体
 */

public class ManagerEntity {
    /**
     * 管理员编号
     */
    private String managerId;

    /**
     * 管理员姓名
     */
    private String managerName;

    /**
     * 管理员昵称
     */
    private String managerNickname;

    /**
     * 管理员密码
     */
    private String managerPassword;

    /**
     *
     */
    private String managerRoleId;

    /**
     * 管理员创建时间
     */
    private Date managerTime;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerNickname() {
        return managerNickname;
    }

    public void setManagerNickname(String managerNickname) {
        this.managerNickname = managerNickname;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public Date getManagerTime() {
        return managerTime;
    }

    public void setManagerTime(Date managerTime) {
        this.managerTime = managerTime;
    }
}
