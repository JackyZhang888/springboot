
-- 测试表初始化
drop database if exists mydatabase;
create database if not exists mydatabase character set utf8;
use mydatabase;
drop table if exists user;
drop table if exists role;
drop table if exists permission;
drop table if exists user_role;
drop table if exists role_permission;
drop table if exists jobs;

-- 用户表
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- 角色表
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `description` varchar(128) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(32) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 用户角色关联表
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 初始化测试数据
INSERT INTO user VALUES(1,'Jacky','123456');
INSERT INTO user VALUES(2,'Mike','123456');
INSERT INTO user VALUES(3,'Tom','123456');
INSERT INTO user VALUES(4,'Lily','123456');

INSERT INTO role VALUES(1,'admin', 'system administrator');
INSERT INTO role VALUES(2,'user', 'ordinary users');

-- 一个管理员，其他普通用户
INSERT INTO user_role VALUES(1,1);
INSERT INTO user_role VALUES(2,2);
INSERT INTO user_role VALUES(3,2);
INSERT INTO user_role VALUES(4,2);

INSERT INTO permission VALUES(1, 'create');
INSERT INTO permission VALUES(2, 'read');
INSERT INTO permission VALUES(3, 'update');
INSERT INTO permission VALUES(4, 'delete');

-- 管理员有增删改查权限，普通用户只有读权限
INSERT INTO role_permission VALUES(1,1);
INSERT INTO role_permission VALUES(1,2);
INSERT INTO role_permission VALUES(1,3);
INSERT INTO role_permission VALUES(1,4);
INSERT INTO role_permission VALUES(2,2);

--任务表
CREATE TABLE `jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `sendMail` int(11) NOT NULL  COMMENT '发送邮件任务',
  `analysisLog` int(11) NOT NULL  COMMENT '分析日志任务',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

INSERT INTO jobs VALUES(1,1,1);
