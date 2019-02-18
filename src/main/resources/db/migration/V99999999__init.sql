insert IGNORE into t_sys_user (id,username,`password`,real_name,gender,email,phone,state,salt)
values(1,'admin','AVxDpmNWIWUAtOql5UwELg==','管理员',1,'','',0,'Sk3rMg==');

INSERT IGNORE INTO t_sys_role (id, role_name)
VALUES(1, '超级管理员');

insert IGNORE into t_sys_role_resource (role_id, resource_id)  select 1,id  from t_sys_resource;

-- insert IGNORE into t_sys_resource
--  (id,resource_name, sort, url, is_display, `level`, `type`, parent_id, parent_ids, permission)
-- values
--  (1,'资源',  100, null, 1, 0, 0, 0, '0/', '') ,
--  (2,'用户管理',  200, '/user', 1, 1, 0, 1, '0/1/', 'user:*') ,
--  (3,'资源管理',  300, '/resource', 1, 1, 0, 1, '0/1/', 'resource:*') ,
--  (4,'角色管理',  500, '/role', 1, 1, 0, 1, '0/1/', 'role:*') ,
--  (5,'组织管理',  600, '/organization', 1, 1, 0, 1, '0/1/', 'organization:*') ,
--  (6,'车辆管理',  700, '/vehicle', 1, 1, 0, 1, '0/1/', 'vehicle:*') ;