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
![1695480200276.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695480209442-6eba5442-0831-42fb-86a0-20768524d863.png#averageHue=%23f8f8f8&clientId=u6debbcac-dd04-4&from=paste&height=716&id=u744aba77&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=87432&status=done&style=none&taskId=u760f35e4-9390-4be4-a999-eeeaab2a3cf&title=&width=1528)
## 接口管理
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695480229027-f2852f47-a0b8-403d-8fb1-ace81bb6cbf1.png#averageHue=%23f8f6f6&clientId=u6debbcac-dd04-4&from=paste&height=716&id=u4da204cf&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=116487&status=done&style=none&taskId=ua66c51cd-bcbc-4449-b58f-46a9af3c1c2&title=&width=1528)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695480242794-1fd1e7bf-5c0c-4cc5-8e81-4085ebebb840.png#averageHue=%23c4c4c4&clientId=u6debbcac-dd04-4&from=paste&height=716&id=u1b682522&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=133772&status=done&style=none&taskId=uf13629f3-af03-46b8-9c74-2e1e6d2bb83&title=&width=1528)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695480256242-187fe1e0-658b-4c71-958e-f541114450ad.png#averageHue=%23f9f9f9&clientId=u6debbcac-dd04-4&from=paste&height=716&id=uc9fe2510&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=81329&status=done&style=none&taskId=u44f1b742-3b8e-4477-8b72-5136482fca0&title=&width=1528)
## 接口在线调式
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695480317549-7cd72243-2a6b-4535-82c6-164a923ce8c1.png#averageHue=%23f8f8f8&clientId=u6debbcac-dd04-4&from=paste&height=716&id=u39e3ec5d&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=115113&status=done&style=none&taskId=u3e2bab28-17df-44dc-b358-3dfc6782cc0&title=&width=1528)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1695480303833-63c9c0d5-9ba5-4bc4-a523-1ed81c44d490.png#averageHue=%23c4c4c4&clientId=u6debbcac-dd04-4&from=paste&height=716&id=u4a487d67&originHeight=895&originWidth=1910&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=100096&status=done&style=none&taskId=u37733bf9-16a8-4be9-bcbe-275ff91f9ce&title=&width=1528)
