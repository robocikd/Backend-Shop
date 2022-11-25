--liquibase formatted sql
--changeset RobocikD:4
alter table product add full_description text not null after description;