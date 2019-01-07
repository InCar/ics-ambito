CREATE TABLE IF NOT EXISTS `t_sys_role_resource` (
  `role_id` bigint NOT NULL  COMMENT '角色id',
  `resource_id` bigint NOT NULL  COMMENT '资源id',
  key(`role_id`),
  key(`resource_id`)
  ) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色资源表';

