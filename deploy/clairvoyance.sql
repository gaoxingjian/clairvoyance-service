/*
 Navicat Premium Data Transfer

 Source Server         : 47.94.204.82
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : 47.94.204.82:3306
 Source Schema         : clairvoyance

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 31/08/2022 01:08:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `src_code` mediumtext COMMENT '源码',
  `hash` varchar(128) DEFAULT NULL,
  `detect_result` mediumtext COMMENT '检测结果',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5196 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '账户名称',
  `password` varchar(200) NOT NULL COMMENT '用户密码密文',
  `salt` varchar(20) DEFAULT NULL COMMENT '加密盐值',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint DEFAULT '1' COMMENT '账户状态(1.正常 2.锁定 )',
  `deleted` tinyint DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

SET FOREIGN_KEY_CHECKS = 1;
