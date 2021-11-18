create table project
(
    id BIGINT auto_increment,
    task_id BIGINT null,
    name VARCHAR(75) not null,
    constraint project_pk
        primary key (id)
);