package com.imyuanxiao.yuanapiclientsdk.config;

import com.imyuanxiao.yuanapiclientsdk.client.YuanApiClient;

import java.util.HashMap;
import java.util.Map;

public class YuanApiClientFactory {

    private final Map<String, YuanApiClient> apiClients;

    public YuanApiClientFactory(Map<String, YuanApiClientConfig.ApiConfig> apiConfigs) {
        apiClients = new HashMap<>();
        for (Map.Entry<String, YuanApiClientConfig.ApiConfig> entry : apiConfigs.entrySet()) {
            String apiName = entry.getKey();
            YuanApiClientConfig.ApiConfig apiConfig = entry.getValue();
            YuanApiClient yuanApiClient = new YuanApiClient(apiConfig.getAccessKey(), apiConfig.getSecretKey());
            apiClients.put(apiName, yuanApiClient);
        }
    }

    public YuanApiClient getClient(String apiName) {
        return apiClients.get(apiName);
    }

}
