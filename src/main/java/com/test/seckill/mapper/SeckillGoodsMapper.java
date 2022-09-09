package com.test.seckill.mapper;

import com.test.seckill.entity.SeckillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 秒杀商品表 Mapper 接口
 * </p>
 *
 * @author pzh
 * @since 2022-09-09
 */
@Mapper
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

}
