package com.test.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Getter
@Setter
@TableName("t_user")
public class User {

    /**
     * 用户ID,手机号码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt)+salt)
     */
    private String password;

    private String salt;

    /**
     * 头像
     */
    private String phone;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 最后一次登录事件
     */
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    private Integer loginCount;


}
