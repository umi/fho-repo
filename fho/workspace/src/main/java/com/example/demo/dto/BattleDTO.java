package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleDTO {

    private String name;
    private Long sum;
    private Long lose;


    public BattleDTO(String name, Long sum ,Long lose) {

        this.name = name;
        this.sum = sum;
        this.lose = lose;

    }
}
