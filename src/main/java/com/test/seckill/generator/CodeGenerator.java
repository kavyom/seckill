package com.test.seckill.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * mybatis-plus代码生成器
 */
public class CodeGenerator {

    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();

        // 此处输入生成代码对应的表名
        tables.add("seckill_order");

        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/seckill?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&useSSL=false&autoReconnect=true&failOverReadOnly=false&autoReconnectForPools=true&zeroDateTimeBehavior=convertToNull&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&tinyInt1isBit=false",
                        "root","root")
            .globalConfig(builder -> {
                builder.author("pzh")  //作者
                        .outputDir(System.getProperty("user.dir")+"\\src\\main\\java")    //输出路径(写到java目录)
//                        .enableSwagger()       //开启swagger
                        .fileOverride()        //开启覆盖之前生成的文件
                        .commentDate("yyyy-MM-dd");
            })
            .packageConfig(builder -> {
                builder.parent("com.test")
                        .moduleName("seckill")
                        .entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .mapper("mapper")
                        .xml("mapper")
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml,System.getProperty("user.dir")+"\\src\\main\\resources\\mapper"));
            })
            .strategyConfig(builder -> {
                builder.addInclude(tables)
                        .addTablePrefix("")

                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")

                        .entityBuilder()
//                        .superClass(BaseEntity.class)
                        .enableLombok()
                        .disableSerialVersionUID()

//                            .logicDeleteColumnName("deleted")
//                            .enableTableFieldAnnotation()

                        .controllerBuilder()
                        .formatFileName("%sController")
                        .enableRestStyle()
                        .enableHyphenStyle()

                        .mapperBuilder()
//                            .enableBaseResultMap()  //生成通用的resultMap
                        .superClass(BaseMapper.class)
                        .formatMapperFileName("%sMapper")
                        .enableMapperAnnotation()
                        .formatXmlFileName("%sMapper");
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }

}


