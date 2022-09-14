package com.test.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.seckill.entity.SeckillOrder;
import com.test.seckill.entity.User;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
public interface SeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     *
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(User user, Long goodsId);
}
