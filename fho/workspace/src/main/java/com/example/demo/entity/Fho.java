package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import com.example.demo.TimeToSecondsConverter;

@Entity
@Data
@Table(name = "fho_info")
public class Fho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private String youtubeId;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String streamStart;
    
    @Column(nullable = true)
    private int total;
    
    @Column(nullable = true)
    private int isMember;
    
    @Column(nullable = true)
    private int isDelete;
	
	public void setContent(List<String> contents){
		int flag = 0;
		String extractedPart = "";
		String date = "";
		String totaltime = "";
		String startTime = "";
		String title = "";
		String streamS = "";
		
		for (String content : contents){
		Pattern datePattern = Pattern.compile("(\\d{1,2}/\\d{1,2})\\s+(\\d{1,2}(:\\d{2})(:\\d{2})?)\\s+(.*?)(\\(\\d{2}:\\d{2}～\\))");
    	Matcher matcherDate = datePattern.matcher(content);
		Pattern pattern = Pattern.compile(".*https://www\\.youtube\\.com/live/([^?]+).*");
        Matcher matcherYouTubeID = pattern.matcher(content);
			
		if(matcherDate.matches()){
		flag++;
		date = matcherDate.group(1);
        totaltime = matcherDate.group(2);
		startTime = matcherDate.group(6);
		title = matcherDate.group(5);
		
		if (startTime != null) {
                startTime = startTime.substring(1, startTime.length() - 2) + ":00"; // "21:27:00" の形式に整形
                streamS = date + " " + startTime;
        }
		}
		if(matcherYouTubeID.matches()){
			flag++;
			extractedPart = matcherYouTubeID.group(1);
		}
		
		}
		        this.youtubeId = extractedPart;
		        this.title = title.trim();
		        this.streamStart = streamS;
				this.total = TimeToSecondsConverter.convertToSeconds(totaltime);
		        this.isMember = 0;
		        this.isDelete = 0;
	}
}