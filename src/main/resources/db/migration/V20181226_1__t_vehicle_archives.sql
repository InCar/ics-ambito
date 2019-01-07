CREATE TABLE IF NOT EXISTS  `t_vehicle_archives` (
  `id` bigint(32) not null auto_increment comment '主键',
  `vin_code` varchar(60) binary not null comment 'vin编码',
  `car_series` varchar(60) binary default null comment '车系',
  `car_type` varchar(60) binary default null comment '车辆型号',
  `car_color` varchar(30) binary default null comment '车辆颜色',
  `plate_no` varchar(10) binary default null comment '车牌号码',
  `configuration` int(10) default null comment '配置 1:低配,2:标配,3:高配',
  `machine_no` varchar(80) default null comment '电机号',
  `gprs_no` varchar(80) binary default null comment 'gprs号',
  `no3g` varchar(80) binary default null comment '3g卡号',
  `factory_name` varchar(80) binary default null comment '产地标识名称',
  `device_type` varchar(40) default null comment '设备型号',
  `sim_number` varchar(30) binary default null comment 'sim卡号',
  `customer_name` varchar(10) binary default null comment '客户姓名',
  `customer_phone` varchar(20) binary default null comment '客户手机号码',
  `gender` varchar(10) binary default null comment '客户性别',
  `birthday` varchar(20) binary default null comment '客户生日',
  `customer_address` varchar(70) binary default null comment '客户地址',
  `certificate_type` varchar(20) binary default null comment '客户证件类型',
  `certificate_no` varchar(50) binary default null comment '客户证件号码',
  `sold_date` datetime default null comment '销售日期',
  `certificate_date` datetime default null comment '合格证日期',
  `car_province` varchar(10) binary default null comment '所属省',
  `car_city` varchar(10) binary default null comment '所属市',
  `remark` varchar(255) binary default '' comment '备注',
  `create_time` timestamp default current_timestamp comment '创建时间',
  `update_time` timestamp default current_timestamp comment '更新时间',
  `create_user_id` bigint(32)  comment '创建用户id',
  `update_user_id` bigint(32)  comment '更新用户id',
  `create_user` varchar(50) binary default '' comment '创建人',
  `update_user` varchar(50) binary default '' comment '更新人',
  `extend_s1` varchar(500) COMMENT '扩展 String 1',
  `extend_s2` varchar(500) COMMENT '扩展 String 2',
  `extend_s3` varchar(500) COMMENT '扩展 String 3',
  `extend_s4` varchar(500) COMMENT '扩展 String 4',
  `extend_s5` varchar(500) COMMENT '扩展 String 5',
  `extend_s6` varchar(500) COMMENT '扩展 String 6',
  `extend_s7` varchar(500) COMMENT '扩展 String 7',
  `extend_s8` varchar(500) COMMENT '扩展 String 8',
  `extend_i1` decimal(19) COMMENT '扩展 Integer 1',
  `extend_i2` decimal(19) COMMENT '扩展 Integer 2',
  `extend_i3` decimal(19) COMMENT '扩展 Integer 3',
  `extend_i4` decimal(19) COMMENT '扩展 Integer 4',
  `extend_f1` decimal(19,4) COMMENT '扩展 Float 1',
  `extend_f2` decimal(19,4) COMMENT '扩展 Float 2',
  `extend_f3` decimal(19,4) COMMENT '扩展 Float 3',
  `extend_f4` decimal(19,4) COMMENT '扩展 Float 4',
  `extend_d1` datetime COMMENT '扩展 Date 1',
  `extend_d2` datetime COMMENT '扩展 Date 2',
  `extend_d3` datetime COMMENT '扩展 Date 3',
  `extend_d4` datetime COMMENT '扩展 Date 4',
  primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8 comment='车辆档案表'




