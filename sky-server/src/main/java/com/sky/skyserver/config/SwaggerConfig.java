package com.sky.skyserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket userApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sky.skyserver.controller"))//过滤的接口
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("服务端接口平台").description("sky-server服务相关数据接口")
                .termsOfServiceUrl("https://www.baidu.com/")
                .license("Licence Version 1.0").version("1.0").build();
    }

}