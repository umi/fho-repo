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
	
	public Details setDetails(String content, int id) {
		Details data = new Details();
		data.setContent(content, id); // 編集ロジックをここに追加できます
		detailsRepository.save(data);

		return data;
	}

}
