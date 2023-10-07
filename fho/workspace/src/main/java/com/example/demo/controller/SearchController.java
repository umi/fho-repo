package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.SearchResultDTO;
import com.example.demo.entity.Mark;
import com.example.demo.service.MarkService;
import com.example.demo.service.SearchService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private MarkService markService;

	@GetMapping("/search")
	public String searchStream(
			@RequestParam(name="sbox1", required=false) String title, 
			@RequestParam(name="sbox2", required=false) String description, 
			@RequestParam(name="mark", required=false) Integer markId, 
			@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "100") int size, 
		    Model model)  {
		
		Page<SearchResultDTO> data;

		int intmarkId = (markId != null) ? markId : 0;
		if(intmarkId == 0) {
			data = searchService.searchByOnlyKeyword(description, title, PageRequest.of(page, size));
		}else {
			data = searchService.searchByKeyword(description, title, intmarkId, PageRequest.of(page, size));
		}
		
		List<Mark> mark = markService.getMark();
		
		model.addAttribute("content", data);
		model.addAttribute("description", description);
		model.addAttribute("title", title);
		model.addAttribute("mark", mark);
		model.addAttribute("selectedValue", markId);
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", data.getTotalPages());
		model.addAttribute("hasNextPage", data.hasNext());
		model.addAttribute("hasPreviousPage", data.hasPrevious());

		
		return "search/index"; // or wherever you want to redirect after saving
	}
}
