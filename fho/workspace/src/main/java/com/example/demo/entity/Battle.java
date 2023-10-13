package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "battle_info")
public class Battle {
    @Id
    private int id;

    @Column(nullable = true)
    private int creativeId;

    @Column(nullable = true)
    private int opponentId;
    
    @Column(nullable = true)
    private int win;
    
    @Column(nullable = true)
    private int lose;
    
    @Column(nullable = true)
    private int sum;
}
