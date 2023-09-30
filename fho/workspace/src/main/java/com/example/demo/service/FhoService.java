package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Fho;
import com.example.demo.repository.FhoRepository;

@Service
public class FhoService {
    @Autowired
    private FhoRepository fhoRepository;

    public List<Fho> getFho() {
        return fhoRepository.findAll();
    }
	
	public void setFho(List<String> contents) {
            Fho data = new Fho();
            data.setContent(contents); // 編集ロジックをここに追加できます
            fhoRepository.save(data);
	}
}