insert IGNORE into t_sys_user (id,username,password,real_name,gender,email,phone,state,salt)
values
(1,'admin','LoiAWka1PI+uJwjCQvkvJg==','管理员',1,'','',0,'1234');


INSERT IGNORE INTO t_sys_role (id, role_name)
VALUES
(1, '超级管理员');

insert into t_sys_role_resource (role_id, resource_id)  select 1,id  from t_sys_resource;