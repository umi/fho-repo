package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Mark;
import com.example.demo.repository.MarkRepository;

@Service
public class MarkService {
	@Autowired
    private MarkRepository markRepository;
	
	public List<Mark> getMark() {
	        return markRepository.findAll();
	}
	
	public Optional<Integer> idFindByMark(String mark) {
		return markRepository.idFindByMark(mark);
	}
}
