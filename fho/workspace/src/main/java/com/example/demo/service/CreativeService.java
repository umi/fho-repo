package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Creative;
import com.example.demo.repository.CreativeRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CreativeService {
	@Autowired
	private CreativeRepository creativeRepository;
	
	private Map<String, Integer> Creative = new HashMap<String, Integer>();
	
	@PostConstruct
	public void init() {
	    this.setCreative();
	}
	
	public List<Creative> getCreative() {
        return creativeRepository.findAll();
	}
	
	public void setCreative() {
		 List<Creative> list = this.getCreative();
		 Creative.clear();
		 
		 for(Creative creative: list) {
			 Creative.put(creative.getCreativeName(), creative.getId());
		 }
		
	}
	
	public void setData(Creative creative) {
		creativeRepository.save(creative);
	}
	
	public int lastInsertId() {
		return creativeRepository.lastInsertId();
	}
	
	public Integer creativeNameToId(String creativeName) {
		
		Integer id = 0;
		
		if(!Creative.containsKey(creativeName)) {
			
			Creative creative = new Creative();
			
			creative.setCreativeName(creativeName);
			creative.setTypeId(0);
			creativeRepository.save(creative);
			id = Integer.valueOf(creativeRepository.lastInsertId());
			this.setCreative();
			
		}else {
			id = Creative.get(creativeName);
		}
		
		return id;
		
	}
}
