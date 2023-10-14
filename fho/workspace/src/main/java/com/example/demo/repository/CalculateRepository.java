package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.BattleDTO;
import com.example.demo.entity.Battle;

public interface CalculateRepository extends JpaRepository<Battle, Integer> {
	@Query("SELECT new com.example.demo.dto.BattleDTO(u.userName AS name, SUM(b.sum) AS sum, SUM(b.lose) AS lose) "
		      + "FROM Battle b LEFT JOIN User u ON b.opponentId = u.id "
		      + "GROUP BY u.userName "
		      + "ORDER BY (1.0 * SUM(b.lose) / SUM(b.sum)) DESC")
    public List<BattleDTO> battleStream();
}
