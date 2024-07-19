package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Battle;
import com.example.demo.repository.BattleRepository;
@Service
public class BattleService {
	
	 @Autowired
	 private BattleRepository battleRepository;
	
	public void setData(Battle battle) {
		battleRepository.save(battle);
	}
}
