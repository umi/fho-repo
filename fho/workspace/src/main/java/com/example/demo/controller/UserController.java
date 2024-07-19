package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/master/user")
public class UserController {
	
	@Autowired
    private UserService userService;
	
    @GetMapping
    public String index(Model model) {
    	List<User> user = userService.getUser();
        model.addAttribute("user", user);
        return "master/user";
    }
}