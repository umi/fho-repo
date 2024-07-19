package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Battle;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Integer> {

}