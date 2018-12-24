CREATE TABLE IF NOT EXISTS  `t_sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新用户id',
  `create_user` varchar(50) DEFAULT '' COMMENT '创建人',
  `update_user` varchar(50) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色表';

