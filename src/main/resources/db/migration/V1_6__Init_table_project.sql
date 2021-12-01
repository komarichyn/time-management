create table project
(
    id BIGINT auto_increment,
    name VARCHAR(75) not null,
    constraint project_pk
        primary key (id)
);

alter table task
    add project_id BIGINT null;