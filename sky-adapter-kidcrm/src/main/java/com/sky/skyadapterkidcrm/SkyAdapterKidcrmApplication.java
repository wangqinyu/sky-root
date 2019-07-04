package com.sky.skyadapterkidcrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = "com.sky.skyentity.entity")//jpa的实体属于子模块中，需配置扫描
public class SkyAdapterKidcrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyAdapterKidcrmApplication.class, args);
    }

}
