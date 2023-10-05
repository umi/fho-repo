package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultDTO {
    private int id;
    private String streamStart;
    private String title;
    private String time;
    private String description;
    private String mark;

    public SearchResultDTO(int id,  String title, String time ,String description, String streamStart, String mark) {
        this.id = id;
        this.streamStart = streamStart;
        this.title = title;
        this.time = time;
        this.description = description;
        this.mark = mark;
    }
}
