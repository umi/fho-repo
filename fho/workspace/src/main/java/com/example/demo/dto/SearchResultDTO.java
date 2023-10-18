package com.example.demo.dto;
import java.sql.Time;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultDTO {
    private int id;
    private int fhoId;
    private LocalDateTime streamStart;
    private String title;
    private Time time;
    private String description;
    private String mark;
    private String youtubeId;

    public SearchResultDTO(int id, int fhoId, String title, Time time ,String description, LocalDateTime streamStart, String mark, String youtubeId) {
        this.id = id;
        this.fhoId = fhoId;
        this.streamStart = streamStart;
        this.title = title;
        this.time = time;
        this.description = description;
        this.mark = mark;
        this.youtubeId = youtubeId;
    }
}