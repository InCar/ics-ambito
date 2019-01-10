CREATE TABLE IF NOT EXISTS `t_sys_org_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `org_id` bigint NOT NULL  COMMENT '组织id',
  `user_id` bigint NOT NULL  COMMENT '用户Id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新用户id',
  `create_user` varchar(50) DEFAULT '' COMMENT '创建人',
  `update_user` varchar(50) DEFAULT '' COMMENT '更新人',
  primary key (`id`),
  KEY (`org_id`),
  KEY (`user_id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='组织用户关系表';