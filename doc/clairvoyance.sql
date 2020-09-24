/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : clairvoyance

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 24/09/2020 17:12:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `src_code` mediumtext COMMENT '源码',
  `hash` varchar(128) DEFAULT NULL,
  `detect_result` mediumtext COMMENT '检测结果',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------
BEGIN;
INSERT INTO `record` VALUES (1, NULL, 'A7FCFC6B5269BDCCE571798D618EA219A68B96CB87A0E21080C2E758D23E4CE9', 'No problem.', '2020-09-24 02:31:04');
INSERT INTO `record` VALUES (4, NULL, 'f6f93ae9a6e987b3dad0d226102c150569e24acf', 'Start Contract Reentrance\n	To analyze：Reentrance.donate(address)\n		cfg分析安全，所以开始ICFG的分析\n	To analyze：Reentrance.balanceOf(address)\n		cfg分析安全，所以开始ICFG的分析\n	To analyze：Reentrance.withdraw(uint256)\n		contract: Reentrance | function: withdraw(uint256) | private: False | 锁/钱提前更新：False\n			path: [\'entryPoint\', \'balances[msg.sender] >= _amount\', \'msg.sender.call.value(_amount)()\']\n[cfg_Reentrancy in] contract: Reentrance . function: withdraw(uint256) | ../../files/reentrancy.sol#15-22\n	To analyze：Reentrance.fallback()\n		cfg分析安全，所以开始ICFG的分析\n', '2020-09-24 02:50:09');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
