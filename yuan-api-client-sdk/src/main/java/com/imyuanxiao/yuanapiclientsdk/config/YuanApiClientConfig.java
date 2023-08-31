package com.imyuanxiao.yuanapiclientsdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("yuanapi.client")
@Data
public class YuanApiClientConfig {

    private Map<String, ApiConfig> apiConfigs = new HashMap<>();

    @Data
    public static class ApiConfig {
        private String accessKey;
        private String secretKey;
    }

    @Bean
    public YuanApiClientFactory ApiClientFactory() {
        return new YuanApiClientFactory(apiConfigs);
    }

}
