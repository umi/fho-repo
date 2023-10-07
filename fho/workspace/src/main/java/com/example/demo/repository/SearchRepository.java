package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.entity.Stream;

public interface SearchRepository extends JpaRepository<Stream, Integer> {
    @Query("SELECT new com.example.demo.dto.SearchResultDTO(d.id,  f.title, d.time, d.description, f.streamStart, m.mark) "
    		+ "FROM Stream d LEFT JOIN Fho f ON d.id = f.id "
    		+ "LEFT JOIN StreamMark sm ON d.streamId = sm.streamId "
    		+ "LEFT JOIN Mark m ON sm.markId = m.markId "
    		+ "WHERE d.description LIKE CONCAT('%', :description, '%') AND f.title LIKE CONCAT('%', :title, '%') AND sm.markId = :markId "
    		+ "ORDER BY f.streamStart DESC")
    public Page<SearchResultDTO> searchStream(@Param("description") String description, @Param("title") String title,  @Param("markId") int markId, PageRequest pageable);
    
    @Query("SELECT new com.example.demo.dto.SearchResultDTO(d.id,  f.title, d.time, d.description, f.streamStart, m.mark) "
    		+ "FROM Stream d LEFT JOIN Fho f ON d.id = f.id "
    		+ "LEFT JOIN StreamMark sm ON d.streamId = sm.streamId "
    		+ "LEFT JOIN Mark m ON sm.markId = m.markId "
    		+ "WHERE d.description LIKE CONCAT('%', :description, '%') AND f.title LIKE CONCAT('%', :title, '%') "
    		+ "ORDER BY f.streamStart DESC")
    public Page<SearchResultDTO> searchOnlyKeywordStream(@Param("description") String description, @Param("title") String title,PageRequest pageable);
}
