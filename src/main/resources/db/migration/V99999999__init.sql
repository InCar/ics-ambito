insert IGNORE into t_sys_user (id,username,`password`,real_name,gender,email,phone,state,salt)
values(1,'admin','hhDT8lNj7jRew1PcvVPRvQ==','管理员',1,'','',0,'Sk3rMg==');

INSERT IGNORE INTO t_sys_role (id, role_name)
VALUES(1, '超级管理员');

insert IGNORE into t_sys_role_resource (role_id, resource_id)  select 1,id  from t_sys_resource;

insert IGNORE into t_sys_org (id, org_name, org_code, parent_code, parent_codes, operate_type,
`level`, is_leaf, status, sort) values (1, 'ROOT_ORG', 'ics', '', '',1,0,0,0,1000);
