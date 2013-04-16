/*创建college数据库*/
drop database if exists college;
create database college;

/*切换到college数据库*/
use college;

/*创建学生表*/
CREATE TABLE student(
   id int NOT NULL AUTO_INCREMENT primary key,
   name varchar(10) NOT NULL,/*姓名*/
   gender char(1) NOT NULL,/*性别*/
   major varchar(20) NOT NULL,/*专业*/
   grade char(4) NOT NULL/*年级*/
);

/*授予college用户访问college数据库的全部权限。为方便起见，用户名与数据库同名。
该用户若不存在则被创建，密码为123456*/
grant all privileges on college.* to college@'%' identified by '123456';
flush privileges;

/*添加记录*/
insert into student(name, gender, major, grade)values('徐大超','男','计算机科学与技术','2008');
insert into student(name, gender, major, grade)values('王梓维','男','计算机科学与技术','2008');
insert into student(name, gender, major, grade)values('陈冲','女','计算机科学与技术','2008');
