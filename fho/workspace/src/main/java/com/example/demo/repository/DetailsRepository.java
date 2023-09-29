package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Details;
import java.util.List;


@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {
	@Query("SELECT d FROM Details d WHERE d.id = ?1")
	public List<Details> findByIdCustom(int id);
}