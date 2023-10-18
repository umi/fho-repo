package com.example.demo.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.entity.Stream;

public interface SearchRepository extends JpaRepository<Stream, Integer> {
    @Query("SELECT Distinct new com.example.demo.dto.SearchResultDTO(f.id, d.fhoId, f.title, d.time, d.description, f.streamStart, m.mark, f.youtubeId) "
    		+ "FROM Stream d LEFT JOIN Fho f ON d.fhoId = f.id "
    		+ "LEFT JOIN StreamMark sm ON d.id = sm.streamId "
    		+ "LEFT JOIN Mark m ON sm.markId = m.id "
    		+ "LEFT JOIN Battle b ON d.id = b.id "
    		+ "LEFT JOIN User u ON b.opponentId = u.id "
    		+ "WHERE d.description LIKE CONCAT('%', :description, '%') AND f.title LIKE CONCAT('%', :title, '%') AND (CASE :markId WHEN 0 THEN 1=1 ELSE sm.markId = :markId END) AND f.streamStart >= :starttime AND f.streamStart <= :endtime AND (CASE :userId WHEN 0 THEN 1=1 ELSE u.id = :userId END ) "
    		+ "ORDER BY f.streamStart DESC")
    public Page<SearchResultDTO> searchStream(@Param("description") String description, @Param("title") String title, @Param("markId") int markId, @Param("starttime") LocalDateTime starttime, @Param("endtime") LocalDateTime endtime , @Param("userId") int userId, PageRequest pageable);
    
    @Query("SELECT new com.example.demo.dto.SearchResultDTO(f.id, d.fhoId, f.title, d.time, d.description, f.streamStart, m.mark, f.youtubeId) "
    		+ "FROM Stream d LEFT JOIN Fho f ON d.fhoId = f.id "
    		+ "LEFT JOIN StreamMark sm ON d.id = sm.streamId "
    		+ "LEFT JOIN Mark m ON sm.markId = m.id "
    		+ "WHERE d.description LIKE CONCAT('%', :description, '%') AND f.title LIKE CONCAT('%', :title, '%') AND f.streamStart >= :strattime AND f.streamStart <= :endtime "
    		+ "ORDER BY f.streamStart DESC")
    public Page<SearchResultDTO> searchOnlyKeywordStream(@Param("description") String description, @Param("title") String title, @Param("strattime") LocalDateTime strattime, @Param("endtime") LocalDateTime endtime ,PageRequest pageable);
}