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
import com.example.demo.service.CalculateService;

@Controller
@RequestMapping("/calculate")
public class CalculateController {
	@Autowired
	private CalculateService calculateService;
	
	 @GetMapping
	    public String index(Model model) {
		 
		 List<BattleDTO> battles = calculateService.clculateBattle();
		 
		    List<CalculateResultDTO> calcs = battles.stream()
                    .map(CalculateResultDTO::fromBattleDTO)
                    .collect(Collectors.toList());
		 
	        model.addAttribute("calc", calcs);
	        return "calculate/index";
	    }
	 
}
