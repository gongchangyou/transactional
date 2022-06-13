本地启动mysql 并新建一张表

-- auto-generated definition
create table test
(
id       int auto_increment comment '主键'
primary key,
value    int default 0 not null comment '值',
version  int default 0 null,
json     varchar(200)  null,
map      varchar(200)  null,
obj_list varchar(200)  null
);

insert into test (value) values (1000);

