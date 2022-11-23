--liquibase formatted sql
--changeset RobocikD:2
alter table product add image varchar(128) after currency;