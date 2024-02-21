--liquibase formatted sql


--changeset andrey:create-table-subject

create table subject
(

    subject_id      bigserial primary key,
    name            text not null,
    type            text not null,
    additional_data text
);