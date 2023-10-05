package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.repository.SearchRepository;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    public List<SearchResultDTO> searchByKeyword(String description, String title, int markId) {
        return searchRepository.searchDetails(description, title, markId);
    }
}
