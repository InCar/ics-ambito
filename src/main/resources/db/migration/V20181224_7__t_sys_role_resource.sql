CREATE TABLE IF NOT EXISTS `t_sys_role_resource` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `role_id` bigint NOT NULL  COMMENT '角色id',
  `resource_id` bigint NOT NULL  COMMENT '资源id',
  primary key(id),
  key(`role_id`),
  key(`resource_id`)
  ) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色资源表';

