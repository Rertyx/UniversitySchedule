--liquibase formatted sql


--changeset andrey:create-table-class

create table class
(
    class_id    bigserial primary key,
    timeslot_id bigint not null,
    room_id     bigint not null,
    subject_id  bigint not null,
    teacher_id  bigint not null
);

--changeset andrey:set_teacher-remove-not-null

alter table class
    alter column teacher_id drop not null;