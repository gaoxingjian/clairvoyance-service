#  Clairvoyance

#### 介绍

Clairvoyance是一个智能合约重入代码检测平台，主要有以下功能：

1. 代码检测：输入代码，点击按钮检测
2. 批量检测：上传含有多个合约文件的压缩包（文件太大需要登录），点击按钮检测，下载检测结果
3. overwrite功能：覆盖之前已有的报告
4. delete功能：根据上传的文件, 从仓库里删除对应的报告
5. 检测指令、文件扩展名、合约仓库路径配置及timeout设定
6. 定时清理合约仓库temp文件
7. 接口文档开关
8. 登陆
9. 注册

#### 演示
[观看演示视频](https://www.bilibili.com/video/BV1Nv411r7kh) 

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

#### application.yml

```yaml
spring:
  application:
    name: clairvoyance
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/clairvoyance?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: asd
  servlet:
    multipart:
      max-file-size: 200MB
      enabled: true
      max-request-size: 200MB
      # multipart上传文件时懒加载
      resolve-lazily: false
      # 上传文件的临时目录
      #location:
  redis:
    host: localhost
    #host: 47.100.164.141
    #password: asd
    port: 6379
    lettuce:
      pool:
        # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-active: 100
        # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-wait: PT10s
        # 连接池中的最大空闲连接 默认 8
        max-idle: 30
        # 连接池中的最小空闲连接 默认 0
        min-idle: 1
    # 链接超时时间
    timeout: PT10S
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    mode: HTML5
    encoding: utf-8
    cache: false
    #enabled: true
    #check-template: true
    #check-template-location: true
mybatis:
  type-aliases-package: com.cav.clairvoyance.domain
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
logging:
  file:
    name: ${logging.file.path}/${spring.application.name}.log
    path: logs
  level:
    com.cav.clairvoyance: debug
file:
  root:
    dir:
      # 合约根目录
      windows: e:\\contract-repository
      mac: /Users/jason/files/contract-repository
      linux: /root/files/contract-repository
  # 文件扩展名
  extension:
    contract: sol
    report: txt
  # overwrite开关, 若为true,则覆盖之前已有的报告; 若为false, 则不覆盖
  overwrite: false
  # delete, 若为true,则根据上传的文件, 从仓库里删除对应的报告; 若为false, 则不删除;
  # 注: 此功能打开时, analyse功能将禁用
  delete: false
cmd:
  # 检测命令
  str: slither --detect ICfgReentrancy
  # 执行timeout 单位ms
  timeout: 300000   #1800000
swagger2:
  # 接口文档开关
  enable: true
#JWT 密钥
jwt:
  secretKey: 78944878877848fg)
  accessTokenExpireTime: PT2H
  refreshTokenExpireTime: PT8H
  refreshTokenExpireAppTime: P30D
  issuer: clairvoyance
```

