/*����college���ݿ�*/
drop database if exists college;
create database college;

/*�л���college���ݿ�*/
use college;

/*����ѧ����*/
CREATE TABLE student(
   id int NOT NULL AUTO_INCREMENT primary key,
   name varchar(10) NOT NULL,/*����*/
   gender char(1) NOT NULL,/*�Ա�*/
   major varchar(20) NOT NULL,/*רҵ*/
   grade char(4) NOT NULL/*�꼶*/
);

/*����college�û�����college���ݿ��ȫ��Ȩ�ޡ�
Ϊ����������û��������ݿ�ͬ�������û�����������
����������Ϊabc123*/
grant all privileges on college.* to college@'%' identified by '123456';
flush privileges;

/*��Ӽ�¼*/
insert into student(name, gender, major, grade)values('���','��','�������ѧ�뼼��','2008');
insert into student(name, gender, major, grade)values('����ά','��','�������ѧ�뼼��','2008');
insert into student(name, gender, major, grade)values('�³�','Ů','�������ѧ�뼼��','2008');