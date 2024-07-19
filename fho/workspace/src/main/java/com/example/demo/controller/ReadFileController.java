package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.LoginUser;
import com.example.demo.service.UserPrincipal;

@Controller
public class ReadFileController {


	@GetMapping("/read")
    public String index() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
    	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    	LoginUser user = userPrincipal.getUser();
    	String authorityCode = user.getAuthorityCode();
    	if(authorityCode.equals("500")) {
    		return "read/index";
    	}else {
    		return "fho/index400";
    	}
        
    }
	
    @GetMapping("/readFile")
    public String readFile(Model model) {
        List<String> content = readFileContent();
        model.addAttribute("content", content);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
    	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    	LoginUser user = userPrincipal.getUser();
    	String authorityCode = user.getAuthorityCode();
    	if(authorityCode.equals("500")) {
    		return "read/index";
    	}else {
    		return "fho/index400";
    	}
        
    }

    public static List<String> readFileContent() {
        ClassPathResource resource = new ClassPathResource("upload/upload.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.singletonList("Error reading file.");
        }
    }
}

