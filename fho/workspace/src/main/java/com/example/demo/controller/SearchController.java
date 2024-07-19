package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.example.demo.entity.User;
import com.example.demo.service.MarkService;
import com.example.demo.service.SearchService;
import com.example.demo.service.UserService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private MarkService markService;
	
	@Autowired
	private UserService userService;
	
	private int sec[] = new int[10000];

	@GetMapping("/search")
	public String searchStream(
			@RequestParam(name="sbox1", required=false) String title, 
			@RequestParam(name="sbox2", required=false) String description, 
			@RequestParam(name="mark", required=false) Integer markId, 
			@RequestParam(name="user", required=false) Integer userId, 
			@RequestParam(name="starttime", required=false) LocalDate starttime, 
			@RequestParam(name="endtime", required=false) LocalDate endtime, 
			@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "100") int size, 
		    Model model)  {
		
		Page<SearchResultDTO> datum;
		int i = 0;
		LocalDate date;
		LocalTime time = LocalTime.of(1, 1);
		LocalDateTime startTime;
		LocalDateTime endTime;


		int intmarkId = (markId != null) ? markId : 0;
		int intuserId = (userId != null) ? userId : 0;
		
		
		if(starttime == null) {
			date = LocalDate.of(2000, 1, 1);
			startTime = LocalDateTime.of(date, time);
		}else {
			startTime = LocalDateTime.of(starttime, time);
		}
		
		if(endtime == null) {
			date = LocalDate.of(2999, 1, 1);
			endTime = LocalDateTime.of(date, time);
		}else {
			endTime = LocalDateTime.of(endtime, time);
		}
		
			datum = searchService.searchByKeyword(description, title, intmarkId, startTime, endTime, intuserId, PageRequest.of(page, size));

		
		List<Mark> mark = markService.getMark();
		List<User> user = userService.getUser();
		
		for(SearchResultDTO data:datum) {
			LocalTime localTime = data.getTime().toLocalTime();
			sec[i] = localTime.toSecondOfDay();
			i++;
		}
		
		
		model.addAttribute("content", datum);
		model.addAttribute("sec", sec);
		model.addAttribute("description", description);
		model.addAttribute("title", title);
		model.addAttribute("mark", mark);
		model.addAttribute("user", user);
		model.addAttribute("selectedMarkValue", markId);
		model.addAttribute("selectedUserValue", userId);
		model.addAttribute("starttime", starttime);
		model.addAttribute("endtime", endtime);
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", datum.getTotalPages());
		model.addAttribute("hasNextPage", datum.hasNext());
		model.addAttribute("hasPreviousPage", datum.hasPrevious());
		
		return "search/index"; // or wherever you want to redirect after saving
	}
}
