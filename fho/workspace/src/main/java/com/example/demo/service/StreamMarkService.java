package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.StreamMarkRepository;

@Service
public class StreamMarkService {
	@Autowired
    private StreamMarkRepository streamMarkRepository;
}
