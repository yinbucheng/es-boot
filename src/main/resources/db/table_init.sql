create table if not exists ad_book(
id bigint primary key auto_increment,
name varchar(25),
title varchar(50),
writer varchar(25),
content text,
create_time datetime not null default now(),
update_time datetime not null default now()
);