package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Details;
import com.example.demo.repository.DetailsRepository;


@Service
public class DetailsService {
    @Autowired
    private DetailsRepository detailsRepository;
	
	public List<Details> findById(Integer id) {
    	return detailsRepository.findByIdCustom(id);
	}

}