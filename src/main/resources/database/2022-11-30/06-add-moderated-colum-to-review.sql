--liquibase formatted sql
--changeset RobocikD:9
ALTER TABLE review
    ADD COLUMN moderated TINYINT NULL DEFAULT 0 AFTER content;