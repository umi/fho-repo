package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserUpdateController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping("/userInsert")
	public String userStream(
			@RequestParam(name="userName", required=false) String userName, 
			@RequestParam(name="userAbbreviation", required=false) String userAbbreviation, 
			@RequestParam(name="id", required=false) int id, 
		    Model model)  {
		
		User user = new User();
		user.setId(id);
		user.setUserName(userName);
		user.setUserAbbreviation(userAbbreviation);
		
		userService.setData(user);
		
		model.addAttribute("user", user);
		
		return "userEdit/edit"; // or wherever you want to redirect after saving
	}
}
