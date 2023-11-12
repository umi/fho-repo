package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Fho;

@Repository
public interface FhoRepository extends JpaRepository<Fho, Integer> {
	@Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
	int lastInsertId();
	
	@Query("SELECT id FROM Fho f WHERE f.youTubeId = :youTubeId")
    List<Integer> findByYouTubeId(String youTubeId);
}
