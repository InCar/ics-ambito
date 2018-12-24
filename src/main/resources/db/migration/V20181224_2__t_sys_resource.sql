CREATE TABLE IF NOT EXISTS `t_sys_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `parent_id` bigint DEFAULT NULL COMMENT '父级id',
  `resource_name` varchar(32) NOT NULL DEFAULT '' COMMENT '资源名称',
  `resource_sort` int DEFAULT NULL COMMENT '资源排序使用字段',
  `url` varchar(255) DEFAULT NULL COMMENT '资源路径，前台配置使用',
  `resource_icon` varchar(255) DEFAULT NULL COMMENT '资源图标，前台配置使用',
  `resource_code` varchar(255) DEFAULT NULL COMMENT '资源编码',
  `is_default` tinyint(4) DEFAULT 1 COMMENT '资源 0 默认不可操作， 1 可操作',
  `resource_type` tinyint(4) DEFAULT 0 COMMENT '资源类型 0 菜单，1 按钮',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新用户id',
  `create_user` varchar(50) DEFAULT '' COMMENT '创建人',
  `update_user` varchar(50) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='资源表';