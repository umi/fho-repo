package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Fho;
import com.example.demo.entity.Details;
import com.example.demo.service.FhoService;
import com.example.demo.service.DetailsService;
import com.example.demo.repository.DetailsRepository;
import com.example.demo.controller.ReadFileController;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Controller
public class ImportController {
	@Autowired
    private DetailsRepository detailsRepository;
	
	@Autowired
    private FhoService fhoService;
	
	@Autowired
    private DetailsService detailsService;

	@GetMapping("/insert")
	public String insertFho(Model model) {
		List<String> contents = ReadFileController.readFileContent();
		fhoService.setFho(contents);
			
		String temp1 = "";
		int id = detailsRepository.findMaxId() + 1;
		List<String> data = new ArrayList<>();	
		for (int i = 0; i + 1 < contents.size(); i++){
		Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}\\s+\\d{1,2}:\\d{2}(?::\\d{2})?\\s+.*?\\(\\d{2}:\\d{2}～\\)");
    	Matcher matcherDate = datePattern.matcher(contents.get(i));
		Pattern pattern = Pattern.compile(".*https://www\\.youtube\\.com/live/([^?]+).*");
        Matcher matcherYouTubeID = pattern.matcher(contents.get(i));
			
		Pattern dPattern = Pattern.compile(".*?\\d{1,2}:\\d{2}(?::\\d{2})?～.*");
		Matcher matcherd = dPattern.matcher(contents.get(i));
		Matcher matcherafter = dPattern.matcher(contents.get(i + 1));
			
			if(!(matcherDate.matches() || matcherYouTubeID.matches())){
				if(matcherd.matches()){	
					temp1 = contents.get(i);
				}
				if(matcherafter.matches() && !temp1.equals("")){
						Details detail = detailsService.setDetails(temp1, id);
						data.add(detail.getTime() + " | " + detail.getDescription());
						temp1 = "";
				}else{
					temp1 = temp1 + " " + contents.get(i + 1);
				}
			}
		
		}

		model.addAttribute("content", data);
		return "read/index"; // or wherever you want to redirect after saving
	}
}
