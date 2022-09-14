package com.test.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.seckill.entity.SeckillOrder;
import com.test.seckill.entity.User;
import com.test.seckill.mapper.SeckillOrderMapper;
import com.test.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (null != seckillOrder) {
            return seckillOrder.getOrderId();
        } else {
            return 0L;
        }
    }
}
