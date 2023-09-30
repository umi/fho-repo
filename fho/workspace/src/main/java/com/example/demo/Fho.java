package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "fho_info")
public class Fho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String youtubeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String streamStart;
    
    @Column(nullable = false)
    private int total;
    
    @Column(nullable = false)
    private int isMember;
    
    @Column(nullable = false)
    private int isDelete;
}