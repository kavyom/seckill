package com.test.seckill.service;

import com.test.seckill.vo.GoodsVo;

import java.util.List;

/**
 * Created by pzh on 2022/9/8.
 */
public interface GoodsService {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 根据商品id获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);

}
