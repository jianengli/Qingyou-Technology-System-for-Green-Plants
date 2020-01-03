create database qingyouSystem  
on
(
Name=Lijianeng_mdf,
Filename='D:\qingyouSystem\lijianeng2.mdf',
Size=100,
Maxsize=500,
Filegrowth=20
)
Log
on
(
Name=Lijianeng_log,
Filename='D:\qingyouSystem\lijianeng2.ldf',
Size=10,
Maxsize=50,
Filegrowth=5
)
go
use qingyouSystem
Go
drop table baby_info
drop table role_of_baby
drop table register_info
create table register_info
(phone_number numeric(13) default(11),
pw numeric(16) default(16),
money numeric(6,2),
register_name nvarchar(20),
sex nvarchar(6),
isChatting numeric(2),
primary key(register_name)
);
create table role_of_baby(
baby_name nvarchar(20),
sex nvarchar(6),
register_name nvarchar(20),
primary key(baby_name),foreign key(register_name)references register_info,
);
create table baby_info(
baby_name nvarchar(20),
sex nvarchar(6),
age nvarchar(3),
temperature nvarchar(6),
humidity nvarchar(6),
light nvarchar(6)
primary key(baby_name),foreign key(baby_name)references role_of_baby,
);
select * from register_info where phone_number='123'
delete from baby_info where sex='ÄÐÐÔ'
delete from role_of_baby where register_name='T'
update register_info set isChatting=0 where register_name='ÀîÄþ'
select count(*)from register_info where isChatting='1'
select * from register_info where isChatting='1'
insert into register_info values (' 12 ', ' 12 ',' 200', ' l ', 'ÄÐÐÔ');
select *
from register_info
select count(*)from register_info
where phone_number='135' and pw='11'
