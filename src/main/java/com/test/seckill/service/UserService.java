package com.test.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.seckill.base.BaseResult;
import com.test.seckill.entity.User;
import com.test.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * @param loginVo
     * @return
     */
    BaseResult login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo);
}
