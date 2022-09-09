package com.test.seckill.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录传参
 */
@Getter
@Setter
@ToString
public class LoginVo {

    @NotNull(message = "手机号不能为空！")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @NotNull(message = "密码不能为空！")
    private String password;
}
