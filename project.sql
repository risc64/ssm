/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.14
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 192.168.1.14:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 26/12/2019 17:37:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `uid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'UID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '陈浩南', '男', '2017-11-02 08:42:59');
INSERT INTO `test` VALUES ('2', '赵梅', '女', '2017-11-03 08:43:03');
INSERT INTO `test` VALUES ('3', '苏大', '女', '2017-11-01 08:43:03');
INSERT INTO `test` VALUES ('4', '王二小', '男', '2017-11-04 08:43:03');
INSERT INTO `test` VALUES ('5', '秦仓', '女', '2017-10-30 08:43:03');
INSERT INTO `test` VALUES ('6', '魏武', '男', '2017-11-06 08:43:03');
INSERT INTO `test` VALUES ('7', '111', '1', '2017-11-06 08:43:03');
INSERT INTO `test` VALUES ('8', '222', '2', '2017-11-06 08:43:03');
INSERT INTO `test` VALUES ('9', '333', '3', '2017-11-06 08:43:03');

SET FOREIGN_KEY_CHECKS = 1;
