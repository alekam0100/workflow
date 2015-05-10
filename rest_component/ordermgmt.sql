drop database if exists ordermgmt;
create database ordermgmt;

create table ordermgmt.test(pk_id_test integer primary key auto_increment, text varchar(45));

insert into ordermgmt.test values(1,"test1");