package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Stream;
import com.example.demo.service.StreamService;

@Controller
@RequestMapping("/stream")
public class StreamController {
    @Autowired
    private StreamService streamService;

	@GetMapping("{id}")
    public String index(@PathVariable("id") Integer id, Model model) {
        List<Stream> stream = streamService.findById(id);
        model.addAttribute("stream", stream);
        return "stream/index";
    }
}
