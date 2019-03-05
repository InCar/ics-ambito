CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `role_id` bigint NOT NULL  COMMENT '角色id',
  `user_id` bigint NOT NULL  COMMENT '用户id',
  primary key(id),
  key (`role_id`),
  key (`user_id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色用户表';

