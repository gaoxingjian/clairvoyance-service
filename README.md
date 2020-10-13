#  Clairvoyance

#### 介绍

Clairvoyance是一个智能合约重入代码检测平台。

#### 架构

平台架构说明

- 核心框架：Spring Boot 2.3.3

- 持久层框架：Mybatis

-  数据库：MySQL 5.7.30

- 接口文档：Swagger 2.9.2

- 前端模板：Thymeleaf + Bootstrap

#### **部署**

- 创建clairvoyance数据库
- 导入clairvoyance.sql
- 启动项目
- 接口文档访问 http://localhost:8080/swagger-ui.html

- Code Test http://localhost:8080/code
- Batch Test http://localhost:8080/batch

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

