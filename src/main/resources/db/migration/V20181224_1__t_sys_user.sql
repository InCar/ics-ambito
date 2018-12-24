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
  PRIMARY KEY (`id`)
  ) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='用户表';


