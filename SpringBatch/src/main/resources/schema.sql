drop table  if EXISTS User;

create table User (
id int PRIMARY key,
name varchar(100) NOT NULL,
email varchar(200) NOT NULL
);