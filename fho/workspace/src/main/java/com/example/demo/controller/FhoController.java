package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Fho;
import com.example.demo.service.FhoService;

@Controller
@RequestMapping("/")
public class FhoController {
    @Autowired
    private FhoService fhoService;

    @GetMapping
    public String index(Model model) {
        List<Fho> fho = fhoService.getFho();
        model.addAttribute("fho", fho);
        return "fho/index";
    }
}