CREATE TABLE `book`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `bookname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书作者',
  `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书状态（可借阅，借阅中,已下架）',
   `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书简介',
	`borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
  `borrowtime` datetime NULL DEFAULT NULL COMMENT '图书借阅时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Java基础案例教程（第2版）', '黑马程序员',  '可借阅', 'Java入门书籍，案例实操丰富', NULL, NULL);
INSERT INTO `book` VALUES (2, 'JavaWeb程序设计任务教程',  '黑马程序员',   '已下架', '使用Java进行Web程序设计的教材', NULL, NULL);
INSERT INTO `book` VALUES (3, 'Java基础入门（第3版）', '黑马程序员',  '借阅中', '介绍Java编程基础知识和核心概念的教材', '张三', '2023-11-06 09:15:30');
INSERT INTO `book` VALUES (4, 'SpringCloud微服务架构开发（第2版）','黑马程序员',  '可借阅', '微服务架构的开发和维护', NULL, NULL);
INSERT INTO `book` VALUES (5, 'SpringBoot企业级开发教程', '黑马程序员', '可借阅', '介绍了Spring Boot的核心概念、使用方法和最佳实践', NULL, NULL);
INSERT INTO `book` VALUES (6, '边城',  '沈从文',  '借阅中','描写湘西边境小镇的小说',  '李四', '2023-11-13 14:17:05');
INSERT INTO `book` VALUES (7, '自在独行', '贾平凹',   '已下架', '生活中的所见所闻和心路历程', NULL, NULL);
INSERT INTO `book` VALUES (8, '围城',  '钱钟书',  '借阅中', '对现代人命运、人生意义进行了探讨', '张三', '2023-11-16 16:44:23');

CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
	`tel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
	`gender` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '黑马', '123', '13112345678','男' , '管理员');
INSERT INTO `user` VALUES (2, '张三', '123',  '13212345678','女' , '顾客');
INSERT INTO `user` VALUES (3, '李四', '123',  '13312345678','男' ,'顾客');

CREATE TABLE `record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '借阅记录id',
  `bookname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅的图书名称',
  `borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
  `borrowtime` datetime NULL DEFAULT NULL COMMENT '图书借阅时间',
  `remandtime` datetime NULL DEFAULT NULL COMMENT '图书归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 'Java基础案例教程（第2版）', '张三', '2023-08-20 11:02:30', '2023-08-28 09:15:30');
INSERT INTO `record` VALUES (2, '自在独行', '李四', '2023-08-19 10:24:30', '2023-10-19 16:41:22');
INSERT INTO `record` VALUES (3, 'Java基础入门（第3版）', '张三', '2023-08-20 14:15:32', '2023-10-14 16:32:12');