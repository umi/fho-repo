package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MarkRepository;

@Service
public class MarkService {
	@Autowired
    private MarkRepository markRepository;
	
	public Optional<Integer> idFindByMark(String mark) {
		return markRepository.idFindByMark(mark);
	}
}
