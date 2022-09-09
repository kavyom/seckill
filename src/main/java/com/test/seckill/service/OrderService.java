package com.test.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.seckill.entity.Order;
import com.test.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
public interface OrderService extends IService<Order> {

    /**
     * 秒杀
     *
     * @param goods
     * @return
     */
    Order seckill(GoodsVo goods);
}
