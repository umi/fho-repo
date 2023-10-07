package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.repository.SearchRepository;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    public Page<SearchResultDTO> searchByKeyword(String description, String title, int markId, PageRequest pageable) {
        return searchRepository.searchStream(description, title, markId, pageable);
    }
    public Page<SearchResultDTO> searchByOnlyKeyword (String description, String title, PageRequest pageable) {
    	return searchRepository.searchOnlyKeywordStream(description, title, pageable);
    }
}
