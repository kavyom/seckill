package com.test.seckill.mapper;

import com.test.seckill.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}
