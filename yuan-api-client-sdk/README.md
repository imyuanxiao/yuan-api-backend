# 接口调用平台SDK

### 简介

本项目为接口调用平台提供给客户的SDK，通过配置文件配置accessKey和secretKey， 客户可以通过SDK快速便捷地调用平台上的接口

### 项目技术选型

* Spring Boot 3
* ...
### 使用方法

在yaml文件内进行如下配置
```yaml
yuanapi:
  client:
      dujitang:
        accessKey: "your-access-key-1"
        secretKey: "your-secret-key-1"
      qinghua:
        accessKey: "your-access-key-2"
        secretKey: "your-secret-key-3"
```

