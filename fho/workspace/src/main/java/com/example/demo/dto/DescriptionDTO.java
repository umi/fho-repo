package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescriptionDTO {

    private String chckpoint;
    private String description;


    public DescriptionDTO(String chckpoint, String description) {
    	 this.chckpoint = chckpoint;
         this.description = description;
    }
}
