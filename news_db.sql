/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : news_db

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 17/05/2025 16:41:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '栏目ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '栏目名称',
  `editor_in_charge` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '栏目负责人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻栏目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (5, '体育新闻', NULL, NULL, NULL);
INSERT INTO `category` VALUES (6, '今日热门', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `news_id` int(11) NULL DEFAULT NULL COMMENT '新闻id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (1, 'user', 1);
INSERT INTO `collect` VALUES (11, 'user', 15);
INSERT INTO `collect` VALUES (13, 'user', 14);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  `category_id` int(11) NOT NULL COMMENT '栏目ID（外键）',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '新闻标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '新闻内容',
  `publish_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻作者',
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻来源',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态（0:草稿，1:已发布，2:已下架）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `look_num` int(11) NULL DEFAULT 0 COMMENT '查看量',
  `collect_num` int(11) NULL DEFAULT 0 COMMENT '收藏量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id`) USING BTREE,
  INDEX `idx_publish_time`(`publish_time`) USING BTREE,
  CONSTRAINT `fk_news_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (14, 5, '今日体育新闻', '坐拥NBA公认现役最强中锋约基奇的丹佛掘金，在本赛季的季后赛开打之后，连续遭遇强敌，第一轮面对哈登和莱昂纳德领军的洛杉矶快船时，不少球迷都认为哈登超强的小打大能力，能够帮助小卡一雪前耻复仇掘金，但靠着约基奇的神勇发挥，掘金苦战七局笑到最后，昂首晋级西部半决赛。\n\n　　第二轮面对打出西部最佳战绩的雷霆，掘金依旧是不被看好的一方，但是在前六场战罢，顽强的掘金在输掉天王山之战的情况下，第六场捍卫主场，再度将大比分扳平，将连续两轮打抢七决战，约基奇和穆雷仍然是掘金队最大的杀手锏，但是重返故地迎战旧主的威少，能否在自己熟悉的场地打出好的表现，也将对最终胜利的归属，形成非常大的影响。好', '2025-05-17 13:28:02', '微博', '微博', 1, NULL, NULL, 8, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐值',
  `locked` tinyint(1) NULL DEFAULT 0 COMMENT '是否锁定',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin', '75292bcd0a750abd91fac254c8688d9ec462fdfd61f017a764d7e3f5799486ef', '7a2d4f1e9b5c8d7e2f1a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a1b2c', 0, '2025-05-16 09:07:29', '2025-05-16 09:07:29');
INSERT INTO `user` VALUES (3, 'user', '75292bcd0a750abd91fac254c8688d9ec462fdfd61f017a764d7e3f5799486ef', '7a2d4f1e9b5c8d7e2f1a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a1b2c', 0, '2025-05-16 10:40:52', '2025-05-16 10:40:59');

SET FOREIGN_KEY_CHECKS = 1;
