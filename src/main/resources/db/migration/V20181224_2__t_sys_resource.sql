CREATE TABLE IF NOT EXISTS `t_sys_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `resource_name` varchar(32) NOT NULL DEFAULT '' COMMENT '资源名称',
  `sort` int NOT NULL DEFAULT 30 COMMENT '资源排序使用字段',
  `url` varchar(255) DEFAULT NULL COMMENT '资源路径，前台配置使用',
  `icon` varchar(255) DEFAULT NULL COMMENT '资源图标，前台配置使用',
  `is_display` bool DEFAULT true COMMENT '是否显示 true=显示，false=隐藏',
  `level` tinyint(4) DEFAULT 0 COMMENT '等级',
  `type` tinyint(4) DEFAULT 0 COMMENT '资源类型 0=菜单，1=按钮',
  `parent_id` bigint DEFAULT NULL COMMENT '父级id',
  `parent_ids` varchar(500) DEFAULT NULL COMMENT '所有父级id',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识符',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新用户id',
  `create_user` varchar(50) DEFAULT '' COMMENT '创建人',
  `update_user` varchar(50) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='资源表';

