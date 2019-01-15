CREATE TABLE IF NOT EXISTS `t_sys_session` (
  `session_id` varchar(200) NOT NULL  COMMENT '会话id',
  `session` blob NOT NULL  COMMENT '会话信息',
  primary key (`session_id`)
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='会话表';