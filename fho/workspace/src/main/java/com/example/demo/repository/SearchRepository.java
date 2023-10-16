package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.entity.Stream;

public interface SearchRepository extends JpaRepository<Stream, Integer> {
    @Query("SELECT Distinct new com.example.demo.dto.SearchResultDTO(f.id,  f.title, d.time, d.description, f.streamStart, m.mark, f.youtubeId) "
    		+ "FROM Stream d LEFT JOIN Fho f ON d.fhoId = f.id "
    		+ "LEFT JOIN StreamMark sm ON d.id = sm.streamId "
    		+ "LEFT JOIN Mark m ON sm.markId = m.id "
    		+ "WHERE d.description LIKE CONCAT('%', :description, '%') AND f.title LIKE CONCAT('%', :title, '%') AND sm.markId = :markId "
    		+ "ORDER BY f.streamStart DESC")
    public Page<SearchResultDTO> searchStream(@Param("description") String description, @Param("title") String title,  @Param("markId") int markId, PageRequest pageable);
    
    @Query("SELECT new com.example.demo.dto.SearchResultDTO(f.id,  f.title, d.time, d.description, f.streamStart, m.mark, f.youtubeId) "
    		+ "FROM Stream d LEFT JOIN Fho f ON d.fhoId = f.id "
    		+ "LEFT JOIN StreamMark sm ON d.id = sm.streamId "
    		+ "LEFT JOIN Mark m ON sm.markId = m.id "
    		+ "WHERE d.description LIKE CONCAT('%', :description, '%') AND f.title LIKE CONCAT('%', :title, '%') "
    		+ "ORDER BY f.streamStart DESC")
    public Page<SearchResultDTO> searchOnlyKeywordStream(@Param("description") String description, @Param("title") String title,PageRequest pageable);
}