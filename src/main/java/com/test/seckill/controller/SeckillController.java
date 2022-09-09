package com.test.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.seckill.common.RespBeanEnum;
import com.test.seckill.entity.Order;
import com.test.seckill.entity.SeckillOrder;
import com.test.seckill.service.GoodsService;
import com.test.seckill.service.OrderService;
import com.test.seckill.service.SeckillOrderService;
import com.test.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 秒杀订单表 前端控制器
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Controller
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, Long goodsId) {
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", 1).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }

        Order order = orderService.seckill(goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }
}
