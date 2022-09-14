package com.test.seckill.controller;


import cn.hutool.json.JSONUtil;
import com.test.seckill.base.BaseResult;
import com.test.seckill.common.RespBeanEnum;
import com.test.seckill.entity.User;
import com.test.seckill.rabbitmq.MQSender;
import com.test.seckill.service.GoodsService;
import com.test.seckill.service.SeckillOrderService;
import com.test.seckill.vo.GoodsVo;
import com.test.seckill.vo.SeckillMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

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
public class SeckillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    @Autowired
    private RedisScript<Long> redisScript;

    /**
     * 初始化，把商品库存数量加载到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("初始化商品库存...");
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("stock:" + goodsVo.getId(), goodsVo.getStockCount());
        });
    }

    @RequestMapping("/doSeckill")
    @ResponseBody
    public BaseResult doSeckill(User user, Long goodsId) {
        // 判断是否重复抢购
        String seckillOrderJson = (String) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (StringUtils.isNotEmpty(seckillOrderJson)) {
            log.info("用户[{}]抢购失败，不能重复抢购！", user.getId());
            return BaseResult.error(RespBeanEnum.REPEATE_ERROR);
        }

        // 预减库存
//        Long stock = redisTemplate.opsForValue().decrement("stock:" + goodsId);
        Long stock = (Long) redisTemplate.execute(redisScript, Collections.singletonList("stock:" + goodsId), Collections.EMPTY_LIST);
        if (stock < 0) {
            log.info("用户[{}]抢购失败，库存不足！", user.getId());
            return BaseResult.error(RespBeanEnum.EMPTY_STOCK);
        }

        // 请求入队，立即返回排队中
        SeckillMessage message = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JSONUtil.toJsonStr(message));
        return BaseResult.success();
    }

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("getResult")
    @ResponseBody
    public BaseResult getResult(User user, Long goodsId) {
        log.info("获取秒杀结果");
        if (user == null) {
            return BaseResult.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return BaseResult.success(orderId);
    }
}
