package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Stream;


@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer> {
	@Query("SELECT d FROM Stream d WHERE d.id = ?1")
	public List<Stream> findByIdCustom(int id);
	
	@Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
	int lastInsertId();
}