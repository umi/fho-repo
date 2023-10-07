package com.example.demo.util;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class EmojiUtil {
	static List<int[]> list = new ArrayList<>();

	static {
		list.add(new int[] { 0x2600, 0x27BF });
		list.add(new int[] { 0x1F300, 0x1F6FF });
		list.add(new int[] { 0x1F900, 0x1F9FF });
		list.add(new int[] { 0x1FA70, 0x1FAFF });
	}

	public static boolean contains(String str) {
		char[] characters = str.toCharArray();
		char high, low;
		int index, limit;
		for (int[] values : list) {
			if (values.length == 1) limit = values[0];
			else limit = values[1];
			for (int codePoint = values[0]; codePoint <= limit; codePoint++) {
				if (codePoint > 0xffff) {
					high = Character.highSurrogate(codePoint);
					low = Character.lowSurrogate(codePoint);
					if ((index = Arrays.binarySearch(characters, (char) high)) >= 0) {
						if (index + 1 < characters.length && characters[index + 1] == (char) low)
							return true;
					}
				} else if (Arrays.binarySearch(characters, (char) values[0]) >= 0)
					return true;
			}
		}
		return false;
	}
}
