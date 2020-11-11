#  Clairvoyance

#### 介绍

Clairvoyance是一个智能合约重入代码检测平台。

#### 架构

平台架构说明

- 核心框架：Spring Boot 2.3.3
- 持久层框架：Mybatis 2.1.3
- 数据库：MySQL 5.7.30
- 缓存框架：Redis 6.0.9
- 安全框架：Shiro 1.4.0
- 无状态 JWT
- 日志框架：Logback
- 接口文档：Swagger 2.9.2
- 前端模板：Thymeleaf + Bootstrap

#### **部署**

- 创建clairvoyance数据库
- 导入clairvoyance.sql
- 启动redis
- 修改application.yml中mysql、redis、合约仓库路径、检测超时时间、检测模式、检测指令等配置信息
- 启动项目
- 项目访问地址http://localhost:8080
- 接口文档访问 http://localhost:8080/swagger-ui.html
- Code Test http://localhost:8080/code
- Batch Test http://localhost:8080/batch
- Guidance http://localhost:8080/guidance
- Login http://localhost:8080/login
- Register http://localhost:8080/register

#### 演示
[观看演示视频](http://localhost:8080/register) 
#### 目录描述     
```
├── README.md	// help
├── clairvoyance	// 应用
│   ├── clairvoyance.iml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml	// Maven配置文件
│   ├── src
│   │   ├── main
│   │   │   ├── java	// java源码
│   │   │   └── resources
│   │   │       ├── application.yml	// 应用配置		
│   │   │       ├── static	// 静态资源
│   │   │       └── templates	// 模版
│   │   └── test
│   └── target
├── doc	// 文档
│   ├── clairvoyance.sql	// 数据库文件
│   ├── detectionReport.txt	// 检测报告样例
│   └── reentrancy.sol	// 检测文件样例
├── files	// 文件
└── logs	// 日志
```

