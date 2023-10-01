package com.example.demo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeToSecondsConverter {
    public static int convertToSeconds(String timeString) {
		if (timeString.isEmpty()) {
			return 0;
		}
        String[] parts = timeString.split(":");
    	
    	if (parts.length == 3) { // 時間が "h:mm:ss" の形式の場合
	        int hour = Integer.parseInt(parts[0]);
	        int minute = Integer.parseInt(parts[1]);
	        int second = Integer.parseInt(parts[2]);
	        return hour*3600+minute*60+second;
    	} else if (parts.length == 2) { // 時間が "m:ss" の形式の場合
	        int minute = Integer.parseInt(parts[0]);
	        int second = Integer.parseInt(parts[1]);
	        return minute*60+second;
    	} else if (parts.length == 1){
    		int second = Integer.parseInt(parts[0]);
    		return second;
    	} else{
        // 不正なフォーマットの場合、原文を返すなどのエラー処理をここに記述します。
        	return 0;
    	}
    }
}
