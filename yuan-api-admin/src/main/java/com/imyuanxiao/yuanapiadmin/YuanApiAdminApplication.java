package com.imyuanxiao.yuanapiadmin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.imyuanxiao.yuanapiadmin.mapper"})
@EnableDubbo
public class YuanApiAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanApiAdminApplication.class, args);
    }

}
