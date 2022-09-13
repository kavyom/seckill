package com.test.seckill.controller;


import com.test.seckill.base.BaseResult;
import com.test.seckill.common.RespBeanEnum;
import com.test.seckill.entity.Order;
import com.test.seckill.entity.User;
import com.test.seckill.service.GoodsService;
import com.test.seckill.service.OrderService;
import com.test.seckill.service.SeckillOrderService;
import com.test.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 秒杀订单表 前端控制器
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Slf4j
@Controller
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/doSeckill")
    @ResponseBody
    public BaseResult doSeckill(Model model, User user, Long goodsId) {
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断库存
        if (goods.getStockCount() < 1) {
            log.info("用户[{}]抢购失败，库存不足！", user.getId());
            return BaseResult.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 判断是否重复抢购
        String seckillOrderJson = (String) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (StringUtils.isNotEmpty(seckillOrderJson)) {
            log.info("用户[{}]抢购失败，不能重复抢购！", user.getId());
            return BaseResult.error(RespBeanEnum.REPEATE_ERROR);
        }

        Order order = orderService.seckill(user, goods);
        return BaseResult.success(order);
    }
}
