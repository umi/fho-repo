package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.BattleDTO;
import com.example.demo.dto.CalculateResultDTO;
import com.example.demo.entity.Fho;
import com.example.demo.service.CalculateService;
import com.example.demo.service.FhoService;
import com.example.demo.util.SecondsToTimeConverter;

@Controller
@RequestMapping("/calculate")
public class CalculateController {
	@Autowired
	private CalculateService calculateService;
	
	@Autowired
	private FhoService fhoService;
	
	 @GetMapping
	    public String index(Model model) {
		 
	        return "calculate/index";
	    }
	 
	 @GetMapping("/user")
	    public String user(Model model) {
		 
		 List<BattleDTO> battles = calculateService.clculateBattle();
		 
		    List<CalculateResultDTO> calcs = battles.stream()
                 .map(CalculateResultDTO::fromBattleDTO)
                 .collect(Collectors.toList());
		 
	        model.addAttribute("calc", calcs);
	        return "calculate/user";
	    }
	 
	 @GetMapping("/fho")
	    public String fho(Model model) {
		 
		 List<Fho> fhoes = fhoService.getFho();
		 
		 int totalTime = 0;
		 int count = 0;
		 String tTime;
		 
		 for(Fho fho: fhoes) {
			 totalTime += fho.getTotal();
			 count++;
		 }
		 tTime = SecondsToTimeConverter.convertToTime(totalTime);
		 
	        model.addAttribute("tTime", tTime);
	        model.addAttribute("count", count);
	        
	        return "calculate/fho";
	    }
}
