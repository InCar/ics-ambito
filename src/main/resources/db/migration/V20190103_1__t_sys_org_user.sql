-- ----------------------------
-- Table structure for t_sys_org_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_org_user`;
CREATE TABLE `t_sys_org_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `org_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '组织机构id',
  `user_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint(32) DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint(32) DEFAULT NULL COMMENT '更新用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织用户关联表';
