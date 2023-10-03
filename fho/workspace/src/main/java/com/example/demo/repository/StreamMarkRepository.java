package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StreamMark;

@Repository
public interface StreamMarkRepository extends JpaRepository<StreamMark, Integer>{

}
