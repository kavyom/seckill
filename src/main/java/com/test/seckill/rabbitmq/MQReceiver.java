package com.test.seckill.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.test.seckill.entity.User;
import com.test.seckill.service.GoodsService;
import com.test.seckill.service.OrderService;
import com.test.seckill.vo.GoodsVo;
import com.test.seckill.vo.SeckillMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息消费者
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderService orderService;


    /**
     * 下单操作
     *
     * @param
     * @return void
     * @author LiChao
     * @operation add
     * @date 6:48 下午 2022/3/8
     **/
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message) {
        log.info("接收消息：" + message);
        SeckillMessage seckillMessage = JSONUtil.toBean(message, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        // 判断库存是否充足
//        if (goodsVo.getStockCount() < 1) {
//            log.info("Mq：用户[{}]抢购失败，库存不足！", user.getId());
//            return;
//        }
//        // 判断是否重复抢购
//        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//        if (seckillOrder != null) {
//            log.info("Mq：用户[{}]抢购失败，不能重复抢购！", user.getId());
//            return;
//        }
        //下单操作
        orderService.seckill(user, goodsVo);
    }
}
