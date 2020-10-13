#  Clairvoyance

智能合约重入代码检测

## 环境

- JDK 1.8
- Spring Boot 2.3.3
- MySQL 5.7.30

## 目录描述

```
├── README.md																			// help
├── clairvoyance																	// 应用
│   ├── clairvoyance.iml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml																		// Maven配置文件
│   ├── src
│   │   ├── main
│   │   │   ├── java															// java源码
│   │   │   └── resources
│   │   │       ├── application.yml								// 应用配置		
│   │   │       ├── static												// 静态资源
│   │   │       └── templates											// 模版
│   │   └── test
│   └── target
├── doc																						// 文档
│   ├── clairvoyance.sql													// 数据库文件
│   ├── detectionReport.txt												// 检测报告样例
│   └── reentrancy.sol														// 检测文件样例
├── files																					// 文件
└── logs																					// 日志
```

