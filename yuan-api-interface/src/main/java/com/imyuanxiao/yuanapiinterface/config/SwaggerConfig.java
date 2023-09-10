package com.imyuanxiao.yuanapiinterface.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description Configuration for swagger
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * <a href="http://localhost:8080/swagger-ui/index.html">swagger</a>
 **/
@Configuration
public class SwaggerConfig {

    @Value("${packages.controller}")
    private String controllerPackages;

    @Value("${swagger.title}")
    private String swaggerTitle;

    @Value("${swagger.description}")
    private String swaggerDescription;

    @Value("${swagger.version}")
    private String swaggerVersion;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(new Info() //
                .title(swaggerTitle) //
                .description(swaggerDescription) //
                .version(swaggerVersion));
    }

}