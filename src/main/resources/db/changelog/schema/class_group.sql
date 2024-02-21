--liquibase formatted sql


--changeset andrey:create-table-class_group

create table class_group
(
    class_id bigint not null,
    group_id int    not null,
    primary key (class_id, group_id)
);