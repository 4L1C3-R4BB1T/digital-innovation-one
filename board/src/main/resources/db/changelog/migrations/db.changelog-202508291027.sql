--liquibase formatted sql
--changeset livia:202508291027
--comment: boards_columns table create

CREATE TABLE boards_columns(
    id          BIGINT          AUTO_INCREMENT  PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    `order`     INTEGER         NOT NULL,
    kind        VARCHAR(7)      NOT NULL,
    boards_id   BIGINT          NOT NULL,
    CONSTRAINT fk_boards_columns_boards
        FOREIGN KEY (boards_id)
        REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT un_id_order
        UNIQUE KEY un_board_id_order(boards_id, `order`)
) ENGINE=InnoDB;

--rollback DROP TABLE boards_columns
