create table if not exists ad_book(
id bigint primary key auto_increment,
name varchar(25),
title varchar(50),
writer varchar(25),
content text,
create_time datetime not null default now(),
update_time datetime not null default now()
);

create table if not exists ad_user(
id bigint  primary key auto_increment,
name varchar(50),
age int,
gender varchar(20) not null default '男' comment `性别 男 女`,
create_time datetime not null default now(),
update_time datetime not null default now()
);