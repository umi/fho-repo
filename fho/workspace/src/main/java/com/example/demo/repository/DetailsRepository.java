package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Details;


@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {
	@Query("SELECT d FROM Details d WHERE d.id = ?1")
	public List<Details> findByIdCustom(int id);
	
	@Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
	int lastInsertId();
}