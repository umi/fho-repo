package com.example.demo.util;

public class SecondsToTimeConverter {
	public static String convertToTime(int seconds) {

	    int hours = seconds / 3600;
	    int remainder = seconds % 3600;
	    int min = remainder / 60;
	    int sec = remainder % 60;
	
	    String formattedTime = String.format("%02d:%02d:%02d", hours, min, sec);
	    
	    return formattedTime;
	}

}
