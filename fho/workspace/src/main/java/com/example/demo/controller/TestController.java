package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {


	@GetMapping("/test")
    public String index(Model model) {
		List<String> content = new ArrayList<>();
		String str = "あい👑うえお😀かきあくけこ🚅Unicode🌏絵文字で遊んでみよう😮";

		// Pattern emojiPattern = Pattern.compile("[\\uD800-\\uDFFF]", Pattern.UNICODE_CASE);
		// Pattern emojiPattern = Pattern.compile("\\X", Pattern.UNICODE_CASE);
		// Pattern emojiPattern = Pattern.compile("\\X");
		// Pattern emojiPattern = Pattern.compile(".", Pattern.UNICODE_CASE);
		Pattern emojiPattern = Pattern.compile(".");
		Matcher emoji = emojiPattern.matcher(str);
		while(emoji.find()){
			if(containsEmoji(emoji.group())){
				content.add(emoji.group());
			}
		}

		content.add("絵文字なし:" + String.valueOf(containsEmoji("絵文字なし")));
		content.add("絵文字あり🐟🍎🍐:" + String.valueOf(containsEmoji("絵文字あり🐟🍎🍐")));

		content.add("end");

        model.addAttribute("content", content);
        return "test/index";
    }

	/**
	 * 絵文字が含まれてるか
	 * @param String
	 * @return boolean
	 */
	private static boolean containsEmoji(String str) {
		int length = str.length();

		for (int i = 0; i < length; i++) {
			int type = Character.getType(str.charAt(i));
			if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
				return true;
			}
		}

		return false;
	}
}

