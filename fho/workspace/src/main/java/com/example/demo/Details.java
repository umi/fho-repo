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
@Table(name = "stream_info")
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int streamId;

    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private int isDelete;
}