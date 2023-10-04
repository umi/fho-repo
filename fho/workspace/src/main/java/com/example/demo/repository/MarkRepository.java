package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer>{
	@Query("select m.markId from Mark m where m.mark = ?1")
	public int idFindByMark(String mark);

}
