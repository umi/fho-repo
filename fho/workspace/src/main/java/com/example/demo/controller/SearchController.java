package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.service.SearchService;

@Controller
public class SearchController {
	
	 @Autowired
	 private SearchService searchService;

	@GetMapping("/search")
	public String searchDetails(@RequestParam(name="s", required=false) String keyword, Model model)  {
		
		List<SearchResultDTO> data = searchService.searchByKeyword(keyword);
		model.addAttribute("content", data);
		return "search/index"; // or wherever you want to redirect after saving
	}
}
