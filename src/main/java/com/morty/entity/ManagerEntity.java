package com.morty.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 管理员实体
 */
@Data
@TableName("manager")
public class ManagerEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 管理员编号
     */
    @TableId
    private int managerId;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String managerPassword;

    /**
     *
     */
    private String managerRoleId;

    /**
     * 管理员创建时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date managerTime;


}
