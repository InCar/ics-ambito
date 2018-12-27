create table `t_vehicle_archives` (
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
  create_time timestamp default current_timestamp comment '创建时间',
  update_time timestamp default current_timestamp comment '更新时间',
  create_user_id bigint(32)  comment '创建用户id',
  update_user_id bigint(32)  comment '更新用户id',
  create_user varchar(50) binary default '' comment '创建人',
  update_user varchar(50) binary default '' comment '更新人',
  primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8 comment='车辆档案表'




