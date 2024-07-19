package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Stream;
import com.example.demo.repository.StreamRepository;


@Service
public class StreamService {
    @Autowired
    private StreamRepository streamRepository;
	
	public List<Stream> findById(Integer id) {
    	return streamRepository.findByIdCustom(id);
	}
	
	public void setData(Stream stream, int id){
		stream.setFhoId(id);
		streamRepository.save(stream);
	}
	
	public int lastInsertId() {
		return streamRepository.lastInsertId();
	}
}
