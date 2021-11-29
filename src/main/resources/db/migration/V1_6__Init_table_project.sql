create table project
(
    id BIGINT auto_increment,
    task_id BIGINT null, todo delete
    name VARCHAR(75) not null,
    constraint project_pk
        primary key (id)
);

alter table task
    add project_id BIGINT null;