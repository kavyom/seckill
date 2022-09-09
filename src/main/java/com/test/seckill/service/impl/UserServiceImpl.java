package com.test.seckill.service.impl;

import com.test.seckill.entity.User;
import com.test.seckill.mapper.UserMapper;
import com.test.seckill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
