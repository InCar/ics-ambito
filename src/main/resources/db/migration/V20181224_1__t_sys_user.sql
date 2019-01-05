CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `username` varchar(50) BINARY NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) BINARY NOT NULL DEFAULT '' COMMENT '密码',
  `real_name` varchar(50) BINARY DEFAULT NULL COMMENT '真实名',
  `gender` int NOT NULL DEFAULT 0 COMMENT '性别 0 男； 1 女',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` tinyint(4) DEFAULT 1 COMMENT '0 禁用 1 可用',
  `salt` varchar (20) DEFAULT NULL COMMENT '盐',
  `create_user` varchar(50) DEFAULT '' COMMENT '创建人',
  `update_user` varchar(50) DEFAULT '' COMMENT '更新人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新用户id',
  `extend_s1` varchar(500) COMMENT '扩展 String 1',
  `extend_s2` varchar(500) COMMENT '扩展 String 2',
  `extend_s3` varchar(500) COMMENT '扩展 String 3',
  `extend_s4` varchar(500) COMMENT '扩展 String 4',
  `extend_s5` varchar(500) COMMENT '扩展 String 5',
  `extend_s6` varchar(500) COMMENT '扩展 String 6',
  `extend_s7` varchar(500) COMMENT '扩展 String 7',
  `extend_s8` varchar(500) COMMENT '扩展 String 8',
  `extend_i1` decimal(19) COMMENT '扩展 Integer 1',
  `extend_i2` decimal(19) COMMENT '扩展 Integer 2',
  `extend_i3` decimal(19) COMMENT '扩展 Integer 3',
  `extend_i4` decimal(19) COMMENT '扩展 Integer 4',
  `extend_f1` decimal(19,4) COMMENT '扩展 Float 1',
  `extend_f2` decimal(19,4) COMMENT '扩展 Float 2',
  `extend_f3` decimal(19,4) COMMENT '扩展 Float 3',
  `extend_f4` decimal(19,4) COMMENT '扩展 Float 4',
  `extend_d1` datetime COMMENT '扩展 Date 1',
  `extend_d2` datetime COMMENT '扩展 Date 2',
  `extend_d3` datetime COMMENT '扩展 Date 3',
  `extend_d4` datetime COMMENT '扩展 Date 4',
  PRIMARY KEY (`id`)
  ) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='用户表';


