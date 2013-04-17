/*在system用户下执行*/

/*添加记录*/
CREATE TABLE student(
   id int NOT NULL  primary key,
   name varchar(20) NOT NULL,/*姓名*/
   gender char(10) NOT NULL,/*性别*/
   major varchar(50) NOT NULL,/*专业*/
   grade char(4) NOT NULL/*年级*/
);

/*添加记录*/
insert into student(id, name, gender, major, grade)values('1','徐大超','男','计算机科学与技术','2008');
insert into student(id, name, gender, major, grade)values('2','王梓维','男','计算机科学与技术','2008');
insert into student(id, name, gender, major, grade)values('3','陈冲','女','计算机科学与技术','2008');
