package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Details;
import com.example.demo.service.DetailsService;

@Controller
@RequestMapping("/details")
public class DetailsController {
    @Autowired
    private DetailsService detailsService;

	@GetMapping("{id}")
    public String index(@PathVariable("id") Integer id, Model model) {
        List<Details> details = detailsService.findById(id);
        model.addAttribute("details", details);
        return "details/index";
    }
}
