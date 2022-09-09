package com.test.seckill.service.impl;

import com.test.seckill.entity.SeckillOrder;
import com.test.seckill.mapper.SeckillOrderMapper;
import com.test.seckill.service.SeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
