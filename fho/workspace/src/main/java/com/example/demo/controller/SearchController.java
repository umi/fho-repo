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
	public String searchDetails(@RequestParam(name="sbox1", required=false) String title, @RequestParam(name="sbox2", required=false) String description, Model model)  {
		
		List<SearchResultDTO> data = searchService.searchByKeyword(description, title);
		model.addAttribute("content", data);
		return "search/index"; // or wherever you want to redirect after saving
	}
}
