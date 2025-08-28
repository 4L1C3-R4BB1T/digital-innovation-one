--liquibase formatted sql
--changeset livia:202508282048
--comment: boards table create

CREATE TABLE boards(
    id      BIGINT          AUTO_INCREMENT  PRIMARY KEY,
    name    VARCHAR(255)    NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS
