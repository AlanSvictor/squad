create table if not exists role
(
    name varchar(40) not null unique,
    constraint role_pkey primary key (name)
);

insert into role(name) values ('Developer');
insert into role(name) values ('Product Owner');
insert into role(name) values ('Tester');

create table if not exists member_role
(
    id          varchar(40) not null,
    team_id     varchar(40) not null,
    user_id     varchar(40) not null,
    role_name   varchar(40) not null,
    constraint member_role_pkey primary key (id),
    unique (team_id, user_id)
);
