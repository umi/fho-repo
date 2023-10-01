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

import com.example.demo.TimeToSecondsConverter;

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
		List<String> data = new ArrayList<>();
		Fho fho = new Fho();

		String title = "";
		String date = "";
		String startTime = "";
		String streamS = "";
			
		String content = "";
		boolean isTitle = false;
		int id = detailsRepository.findMaxId() + 1;
		for (int i = 0; i + 1 < contents.size(); i++){
			Pattern datePattern = Pattern.compile("(\\d{1,2}/\\d{1,2})\\s+(\\d{1,2}:\\d{2}(?::\\d{2})?)\\s*([^\\(]*)(?:\\((\\d{2}:\\d{2})～\\))?(.*)");
			Matcher matcherDate = datePattern.matcher(contents.get(i));
			Pattern pattern = Pattern.compile("^\\s*https://www\\.youtube\\.com/live/([^?]+).*");
			Matcher matcherYouTubeID = pattern.matcher(contents.get(i));
				
			Pattern dPattern = Pattern.compile("^\\s*[^\\(]*?\\d{1,2}:\\d{2}(?::\\d{2})?～\\s*.*");
			Matcher matcherd = dPattern.matcher(contents.get(i));
			Matcher matcherafter = dPattern.matcher(contents.get(i + 1));
			
			if(matcherYouTubeID.matches()){
				fho.setYoutubeId(matcherYouTubeID.group(1));
			}else{
				if(matcherDate.matches()){
					date = matcherDate.group(1);
					startTime = matcherDate.group(4);
					content = (matcherDate.group(3) != null ? matcherDate.group(3) : "") + (matcherDate.group(5) != null ? matcherDate.group(5) : "");
					// content = matcherDate.group(3) + matcherDate.group(5);
					content = content.trim();
					if (startTime != null) {
						streamS = date + " " + startTime;
					}
					// fho.setTitle(content);
					fho.setStreamStart(streamS);
					fho.setTotal(TimeToSecondsConverter.convertToSeconds(matcherDate.group(2)));
					fho.setIsMember(0);
					fho.setIsDelete(0);

					isTitle = true;
				}else if(matcherd.matches()){	
					content = contents.get(i).trim();
					isTitle = false;
				}
				if(matcherafter.matches() && (!content.isEmpty() || isTitle)){
					if(isTitle){
						fho.setTitle(content);
						isTitle = false;
					}else{
						Details detail = detailsService.setDetails(content.trim(), id);
						data.add(detail.getTime() + " | " + detail.getDescription());
					}
					content = "";
				}else{
					content = content + " " + contents.get(i + 1).trim();
				}
			}
		}

		fhoService.setFho(fho);
		data.add(0, fho.getStreamStart() + " | " + fho.getTitle());

		model.addAttribute("content", data);
		return "read/index"; // or wherever you want to redirect after saving
	}
}
