package com.example.demo.controller;

import java.time.LocalDateTime;
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
		 int mtotalTime[] = new int[128];
		 int mcount[] = new int[128];
		 int count = 0;
		 int j = 0;
		 
		 String tTime;
		 String mTime[] = new String[128];
		 
		 for(Fho fho: fhoes) {
			 totalTime += fho.getTotal();
			 count++;
		 }
		 tTime = SecondsToTimeConverter.convertToTime(totalTime);
		 
		// 現在の年を取得
		 int Year;
		 
		 
			 for(Fho fho: fhoes) {
				
				 Year = LocalDateTime.now().getYear();
				 j = 0;
				 
				while(Year >= 2021) {
					 for(int i = 0; i < 12; i++) {
				            LocalDateTime startOfMonth = LocalDateTime.of(Year, i + 1, 1, 0, 0); // i+1 で1月から12月を表現
				            LocalDateTime endOfMonth = startOfMonth.plusMonths(1); // 次の月の1日の0時
	
				            if(fho.getStreamStart().isAfter(startOfMonth) && fho.getStreamStart().isBefore(endOfMonth)) {
				                mtotalTime[i+12*j] += fho.getTotal();
				                mcount[i+12*j]++;
				            }
				      }
					 j++;
					 Year--;
				 }
			 }
			 for(int i = 0; i < 36; i++) {
				 mTime[i] = SecondsToTimeConverter.convertToTime(mtotalTime[i]);
			 }
		 
		
		 
	        model.addAttribute("tTime", tTime);
	        model.addAttribute("count", count);
	        model.addAttribute("mTime", mTime);
	        model.addAttribute("mcount", mcount);
	        
	        return "calculate/fho";
	    }
}
