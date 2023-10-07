package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentEmojiExtractor {

	public static List<String> extractEmojis(String content){
		
		List<String> emojis = new ArrayList<>();
		
		Pattern emojiPattern = Pattern.compile(".");
		Matcher emoji = emojiPattern.matcher(content);
		while(emoji.find()){
			if(containsEmoji(emoji.group())){
				emojis.add(emoji.group());
			}
		}
        return emojis;
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