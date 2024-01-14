
-- 测试表初始化
drop database if exists mydatabase;
create database if not exists mydatabase character set utf8;
use mydatabase;
drop table if exists user;

-- 用户表
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- 初始化测试数据
INSERT INTO user VALUES(1,'Jacky','123456');
INSERT INTO user VALUES(2,'Mike','123456');
INSERT INTO user VALUES(3,'Tom','123456');
INSERT INTO user VALUES(4,'Lily','123456');
