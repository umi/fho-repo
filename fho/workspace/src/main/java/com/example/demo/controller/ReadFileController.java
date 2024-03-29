package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReadFileController {


	@GetMapping("/read")
    public String index() {
        return "read/index";
    }
	
    @GetMapping("/readFile")
    public String readFile(Model model) {
        List<String> content = readFileContent();
        model.addAttribute("content", content);
        return "read/index";
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

