package com.test.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by pzh on 2022/9/8.
 */
@Getter
@Setter
@TableName("t_goods")
public class Goods {

    /** 商品ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 商品名称 **/
    private String goodsName;

    /** 商品标题 **/
    private String goodsTitle;

    /** 商品图片 **/
    private String goodsImg;

    /** 商品详情 **/
    private String goodsDetail;

    /** 商品价格 **/
    private BigDecimal goodsPrice;

    /** 商品库存，-1表示没有限制 **/
    private Integer goodsStock;
}
