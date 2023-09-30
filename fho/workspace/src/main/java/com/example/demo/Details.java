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
		
		Pattern dPattern = Pattern.compile(".*(\\d{1,2}(:\\d{2})(:\\d{2})?)～(.*)");
		Matcher matcherd = dPattern.matcher(content);
		if(matcherd.matches()){
			timeString = matcherd.group(1);
		}
		String[] parts = timeString.split(":");
		if (parts.length == 3) { // 時間が "h:mm:ss" の形式の場合
			if(parts[0].length() == 2){
				time = timeString;
			}else{
				time = "0" + timeString;
			}
    	} else if (parts.length == 2) { // 時間が "m:ss" の形式の場合
	        if(parts[0].length() == 2){
    			time = "00:" + timeString;
	        }else{
	        	time = "00:0" + timeString;
	        }
    	} else if (parts.length == 1){
    		if(parts[0].length() == 2){
    			time = "00:00" + timeString;
    		}else{
    			time = "00:00:0" + timeString;
    		}
    	} else{
        // 不正なフォーマットの場合、原文を返すなどのエラー処理をここに記述します。
        	time = "00:00:00";
    	}
		
		        this.id = id;
		        this.time = time;
		        this.description = matcherd.group(4);
		        this.isDelete = 0;
	}
}