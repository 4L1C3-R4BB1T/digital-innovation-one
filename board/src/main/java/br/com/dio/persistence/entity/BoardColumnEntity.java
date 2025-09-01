package br.com.dio.persistence.entity;

import lombok.Data;

@Data
public class BoardColumnEntity {

    private Long id;
    private String name;
    private Integer order;
    private BoardColumnKindEnum kind;
    private BoardEntity board = new BoardEntity();

}
