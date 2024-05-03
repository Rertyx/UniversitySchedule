--liquibase formatted sql


--changeset andrey:create-table-subject

create table subject
(

    subject_id      bigserial primary key,
    name            text not null,
    type            text not null,
    additional_data text
);

--changeset andrey:alter-table-unique
CREATE UNIQUE INDEX id_name_type_additional_data
    ON subject (name, type, additional_data) nulls not distinct;