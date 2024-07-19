package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	private Map<String, Integer> User = new HashMap<String, Integer>();
	
	@PostConstruct
	public void init() {
	    this.setUser();
	}
	
	public List<User> getUser() {
        return userRepository.findAll();
	}
	
	public Optional<User> findById(Integer id) {
    	return userRepository.findById(id);
	}
	
	public void setUser() {
		 List<User> list = this.getUser();
		 User.clear();
		 
		 for(User user: list) {
			 User.put(user.getUserAbbreviation(), user.getId());
		 }
		
	}
	
	public void setData(User user) {
		userRepository.save(user);
	}
	
	public void updateData(int id, String userName, String userAbbreviation) {
		User user = new User();
		user.setId(id);
		user.setUserName(userName);
		user.setUserAbbreviation(userAbbreviation);
		userRepository.save(user);
	}
	
	public int lastInsertId() {
		return userRepository.lastInsertId();
	}
	
	public Integer userAbbreviationToId(String userAbbreviation) {
		
		Integer id = 0;
		
		if(!User.containsKey(userAbbreviation)) {
			
			User user = new User();
			
			user.setUserAbbreviation(userAbbreviation);
			user.setUserName("");
			userRepository.save(user);
			id = Integer.valueOf(userRepository.lastInsertId());
			this.setUser();
			
		}else {
			id = User.get(userAbbreviation);
		}
		
		return id;
		
	}
}
