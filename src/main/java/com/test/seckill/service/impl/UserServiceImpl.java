package com.test.seckill.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.seckill.base.BaseResult;
import com.test.seckill.entity.User;
import com.test.seckill.exception.BusinessException;
import com.test.seckill.mapper.UserMapper;
import com.test.seckill.service.UserService;
import com.test.seckill.util.CookieUtil;
import com.test.seckill.util.MD5Util;
import com.test.seckill.util.UUIDUtil;
import com.test.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BaseResult login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {
        //根据手机号获取用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, loginVo.getMobile());
        User user = userMapper.selectOne(queryWrapper);
        if (null == user) {
            throw new BusinessException(10001, "用户名不存在");
        }
        //校验密码
        if (!MD5Util.md5(loginVo.getMobile(), user.getSalt()).equals(user.getPassword())) {
            throw new BusinessException(10002, "密码不正确");
        }

        //生成cookie
        String ticket = UUIDUtil.uuid();
//        request.getSession().setAttribute(ticket, user);
        redisTemplate.opsForValue().set("user_" + ticket, JSONUtil.toJsonStr(user));
        CookieUtil.setCookie(request, response,"userTicket", ticket);

        return BaseResult.success(ticket);
    }

    @Override
    public User getUserByCookie(HttpServletRequest request, HttpServletResponse response, String userTicket) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }

        String jsonStr = (String) redisTemplate.opsForValue().get("user_" + userTicket);
        User user = JSONUtil.toBean(jsonStr, User.class);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }


}
