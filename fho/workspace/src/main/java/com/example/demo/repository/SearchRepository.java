package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.entity.Details;

public interface SearchRepository extends Repository<Details, Integer> {
    @Query("SELECT new com.example.demo.dto.SearchResultDTO(d.id,  f.title, d.time, d.description, f.streamStart) FROM Details d LEFT JOIN Fho f ON d.id = f.id WHERE d.description like CONCAT('%', :keyword, '%')")
    public List<SearchResultDTO> searchDetails(@Param("keyword") String keyword);
}
