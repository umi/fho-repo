package com.example.demo.entity;

import java.time.LocalDateTime;

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

    @Column(nullable = true)
    private String youtubeId;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private LocalDateTime streamStart;
    
    @Column(nullable = true)
    private int total;
    
    @Column(nullable = true)
    private int isMember;
    
    @Column(nullable = true)
    private int isDelete;
}
