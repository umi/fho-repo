package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Fho;

@Repository
public interface FhoRepository extends JpaRepository<Fho, Integer> {
	@Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
	int lastInsertId();
}
