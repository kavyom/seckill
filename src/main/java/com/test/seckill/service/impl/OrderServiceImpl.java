package com.test.seckill.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.seckill.entity.Order;
import com.test.seckill.entity.SeckillGoods;
import com.test.seckill.entity.SeckillOrder;
import com.test.seckill.entity.User;
import com.test.seckill.mapper.OrderMapper;
import com.test.seckill.service.OrderService;
import com.test.seckill.service.SeckillGoodsService;
import com.test.seckill.service.SeckillOrderService;
import com.test.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public synchronized Order seckill(User user, GoodsVo goods) {
        //秒杀商品表减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new
                QueryWrapper<SeckillGoods>().eq("goods_id",
                goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .set("stock_count", seckillGoods.getStockCount())
                .eq("id", seckillGoods.getId())
                .gt("stock_count", 0));

        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        // 缓存
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), JSONUtil.toJsonStr(seckillOrder));

        log.info("用户[{}]秒杀商品成功！", user.getId());
        return order;
    }
}
