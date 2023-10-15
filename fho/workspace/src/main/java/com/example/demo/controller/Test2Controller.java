package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.util.DocumentParser;

@Controller
public class Test2Controller {


	@GetMapping("/test2")
    public String index(Model model) {
		List<String> content = new ArrayList<>();

		Pattern headPattern = DocumentParser.getHeadLinePattern();

		// Matcher matcherHead = headPattern.matcher("9/23    11:18:23    練習や、マジ(10:56～)");
		// Matcher matcherHead = headPattern.matcher("3:10:44    今日何時間やんねん、マジ(22:16～)");
		// Matcher matcherHead = headPattern.matcher("  0:18～あーしんど");
		Matcher matcherHead = headPattern.matcher("①10/1    3:30:12    夜中(2:13～)");

		if(matcherHead.matches()){
			content.add(matcherHead.group());

			content.add(matcherHead.group(1));
			content.add(matcherHead.group(2));
			content.add(matcherHead.group(3));
			content.add(matcherHead.group(4));
		}

        model.addAttribute("content", content);
        return "test/index";
    }
}

