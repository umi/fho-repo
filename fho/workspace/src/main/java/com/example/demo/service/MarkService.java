package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MarkRepository;

@Service
public class MarkService {
	@Autowired
    private MarkRepository markRepository;
}
