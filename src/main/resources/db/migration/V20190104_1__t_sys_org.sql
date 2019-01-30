CREATE TABLE `t_sys_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id 自增',
  `org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织名称',
  `org_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织code',
  `parent_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父组织code',
  `parent_codes` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所有父组织codes',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '邮件',
  `operate_type` tinyint(4) DEFAULT '0' COMMENT '是否可操作 0=不可操作，1=可操作',
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '等级',
  `is_leaf` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为叶子节点 0=非叶子节点，1=叶子结点',
  `full_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '机构全名',
  `leader` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` bigint(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '联系地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态，0=可用，1=逻辑删除',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `sort` int(11) NOT NULL DEFAULT '30' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新用户id',
  `create_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建人',
  `update_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '更新人',
  `extend_s1` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 1',
  `extend_s2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 2',
  `extend_s3` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 3',
  `extend_s4` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 4',
  `extend_s5` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 5',
  `extend_s6` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 6',
  `extend_s7` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 7',
  `extend_s8` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展 String 8',
  `extend_i1` decimal(19,0) DEFAULT NULL COMMENT '扩展 Integer 1',
  `extend_i2` decimal(19,0) DEFAULT NULL COMMENT '扩展 Integer 2',
  `extend_i3` decimal(19,0) DEFAULT NULL COMMENT '扩展 Integer 3',
  `extend_i4` decimal(19,0) DEFAULT NULL COMMENT '扩展 Integer 4',
  `extend_f1` decimal(19,4) DEFAULT NULL COMMENT '扩展 Float 1',
  `extend_f2` decimal(19,4) DEFAULT NULL COMMENT '扩展 Float 2',
  `extend_f3` decimal(19,4) DEFAULT NULL COMMENT '扩展 Float 3',
  `extend_f4` decimal(19,4) DEFAULT NULL COMMENT '扩展 Float 4',
  `extend_d1` datetime DEFAULT NULL COMMENT '扩展 Date 1',
  `extend_d2` datetime DEFAULT NULL COMMENT '扩展 Date 2',
  `extend_d3` datetime DEFAULT NULL COMMENT '扩展 Date 3',
  `extend_d4` datetime DEFAULT NULL COMMENT '扩展 Date 4',
  `zip_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'zipcode',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `org_code` (`org_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='组织表';