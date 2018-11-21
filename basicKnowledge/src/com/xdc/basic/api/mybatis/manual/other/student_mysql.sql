-- 创建college数据库
drop database if exists college;
create database college default charset utf8 collate utf8_general_ci;

-- 切换到college数据库
use college;

-- 创建学生表
create table student
(
    id int not null auto_increment,
    name varchar(10) default '' not null comment '姓名',
    gender char(1) default '' not null comment '性别',
    major varchar(20) default '' not null comment '专业',
    grade char(4) default '' not null comment '年级',
    primary key (id),
    index idx_name (name),
    index idx_major (major)
) engine=InnoDB default charset=utf8mb4 comment='学生表';

-- 授予college用户访问college数据库的全部权限。为方便起见，用户名与数据库同名。该用户若不存在则被创建，密码为123456
grant all privileges on college.* to college@'%' identified by '123456';
flush privileges;

-- 添加记录
insert into student (name, gender, major, grade) values ('徐大超', '男', '计算机科学与技术', '2008');
insert into student (name, gender, major, grade) values ('王梓维', '男', '计算机科学与技术', '2008');
insert into student (name, gender, major, grade) values ('陈冲', '女', '计算机科学与技术', '2008');
