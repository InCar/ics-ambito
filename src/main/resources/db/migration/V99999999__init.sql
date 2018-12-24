insert into t_sys_user (id,username,password,real_name,gender,email,phone,state,salt)
values
(1,'admin','LoiAWka1PI+uJwjCQvkvJg==','张丙',1,'1234@qq.com','18627720789',0,'1234'),
(2,'liding','LoiAWka1PI+uJwjCQvkvJg==','李丁',1,'3344@qq.com','18627720780',0,'1234'),
(3,'wangwu','LoiAWka1PI+uJwjCQvkvJg==','王戊',1,'abcdef@qq.com','18627720781',0,'1234');


INSERT INTO t_sys_role (id, role_name)
VALUES
(1, '超级管理员'),
(2, '操作员');

 insert into t_sys_role_resource (role_id, resource_id)  select 1,id  from t_sys_resource;
 insert into t_sys_role_resource (role_id, resource_id)  select 2,id  from t_sys_resource;
