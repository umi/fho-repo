package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Entity
@Data
@Table(name = "stream_info")
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int streamId;

    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private int isDelete;
	
	public void setContent(String content, int id){
		String timeString = "";
		String time = "";
		Pattern dPattern = Pattern.compile("\\s*([^\\(]*?)(\\d{1,2}:\\d{2}(?::\\d{2})?)～\\s*(.*)");
		Matcher matcherd = dPattern.matcher(content);
		if(matcherd.matches()){
			timeString = matcherd.group(2);
			time = "00:00:00".substring(0, 8 - timeString.length()) + timeString;
		}else{
			// 不正なフォーマットの場合、原文を返すなどのエラー処理をここに記述します。
			time = "00:00:00";
		}
		this.id = id;
		this.time = time;
		this.description = (matcherd.group(1) + " " + matcherd.group(3)).trim();
		this.isDelete = 0;
	}

	public String getTime(){
		return this.time;
	}

	public String getDescription(){
		return this.description;
	}
}
