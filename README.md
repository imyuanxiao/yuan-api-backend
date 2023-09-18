# 项目简介
Api Master 是一个提供API接口供开发者调用的平台。管理员可以创建并发布接口，统计分析各接口调用情况；用户可以注册登录并开通接口，浏览接口及在线调用接口，并且可以使用客户端SDK在个人项目中调用接口。
本项目前端使用Ant Design Pro脚手架进行构建，后端基于 Spring Boot 进行搭建，后端分为5个子项目：

1. yuan-api-admin：管理后端，包含接口管理、用户管理等相关功能，以及在线调试的请求转发。
2. yuan-api-gateway：网关，对接口调用进行统一的路由转发、负载均衡、流量染色，进行统一的接口调用次数统计等功能。
3. yuan-api-interface：模拟接口，用于接受用户请求并返回信息，可在改模块中增加新接口。
4. yuan-api-common：公共项目，封装公用类，包括统一异常处理、统一响应处理、统一响应类等。
5. yuan-api-client-sdk：根据模拟接口定制的SDK，使用户可以通过SDK里统一的调用方法调用接口。

前端仓库：
[https://github.com/imyuanxiao/yuan-api-frontend](https://github.com/imyuanxiao/yuan-api-frontend)
后端仓库：
[https://github.com/imyuanxiao/yuan-api-backend](https://github.com/imyuanxiao/yuan-api-backend)
# 技术选型
## 前端

1. React
2. Ant Design Pro 5.x
## 后端

1. Spring Boot
2. MySQL数据库
3. MyBatis-Plus 及 MyBatis X 自动生成
4. API 签名认证(Http 调用)
5. Dubbo 分布式(RPC、Nacos)
6. Swagger接口文档生成
7. Spring Cloud Gateway
8. Hutool等工具库
# 项目截图
## 接口商店
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695016626898-e6c8e015-7746-4207-b1d9-e49cbb02d74f.png#averageHue=%23f8f8f8&clientId=ub98bf37e-a159-4&from=paste&height=716&id=kmrQZ&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=104233&status=done&style=none&taskId=ufe94f8d1-6ce9-4d76-a3c6-101b4af80fa&title=&width=1528)
## 接口管理
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695016587287-c723ebc6-b9b1-4105-b9c6-c3aa5d370bbb.png#averageHue=%23f8f6f6&clientId=ub98bf37e-a159-4&from=paste&height=716&id=Efl6o&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=112867&status=done&style=none&taskId=u31630e24-be6f-43bd-af7b-54ef1a3a465&title=&width=1528)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695016607574-ed730f78-e944-4e82-88de-88ca604c5fa5.png#averageHue=%23c5c4c4&clientId=ub98bf37e-a159-4&from=paste&height=716&id=h4OJn&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=136560&status=done&style=none&taskId=u349f1784-1229-442e-b8cb-c9977aa9c65&title=&width=1528)
## 接口在线调式
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695016576513-200f7a7e-9089-4347-8bb0-aa68e17a0027.png#averageHue=%23f8f8f8&clientId=ub98bf37e-a159-4&from=paste&height=716&id=u06b6b35d&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=116821&status=done&style=none&taskId=u0058240d-9f8f-4271-a015-c5b28678823&title=&width=1528)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695016617544-6e7696f5-e741-4a88-b531-142e637dee64.png#averageHue=%23c4c4c4&clientId=ub98bf37e-a159-4&from=paste&height=716&id=udaef8b6d&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=106380&status=done&style=none&taskId=u7adaf086-fb27-435a-9757-aa64cff3671&title=&width=1528)

