package com.test.seckill.mapper;

import com.test.seckill.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
