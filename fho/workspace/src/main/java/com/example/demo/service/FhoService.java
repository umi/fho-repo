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

	public int lastInsertId() {
		return fhoRepository.lastInsertId();
	}
	
	public void setData(Fho fho) {
		fhoRepository.save(fho);
	}
}
