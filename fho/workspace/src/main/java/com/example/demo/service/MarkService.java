package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Mark;
import com.example.demo.repository.MarkRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MarkService {
	
	@Autowired
    private MarkRepository markRepository;
	
	private Map<String, Integer> Marks = new HashMap<String, Integer>();
	
	@PostConstruct
	public void init() {
	    this.setMarks();
	}
	
	public List<Mark> getMark() {
	        return markRepository.findAll();
	}
	
	public Optional<Integer> idFindByMark(String mark) {
		return markRepository.idFindByMark(mark);
	}
	
	public void setData(Mark mark){
		markRepository.save(mark);
	}
	
	public int lastInsertId() {
		return markRepository.lastInsertId();
	}
	
	public Integer markToId(String mark) {
		
		Integer id = 0;
		
		if(!Marks.containsKey(mark)) {
			
			Mark imark = new Mark();
			
			imark.setMark(mark);
			imark.setMarkDescription("");
			markRepository.save(imark);
			id = Integer.valueOf(markRepository.lastInsertId());
			this.setMarks();
			
		}else {
			id = Marks.get(mark);
		}
		
		return id;
	}
	
	public void setMarks() {
		 List<Mark> list = this.getMark();
		 Marks.clear();
		 
		 for(Mark mark: list) {
			 Marks.put(mark.getMark(), mark.getMarkId());
		 }
		
	}
}
