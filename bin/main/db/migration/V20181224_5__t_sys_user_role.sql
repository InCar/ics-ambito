CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `role_id` bigint NOT NULL  COMMENT '角色id',
  `user_id` bigint NOT NULL  COMMENT '用户id',
  key (`role_id`),
  key (`user_id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色用户表';

