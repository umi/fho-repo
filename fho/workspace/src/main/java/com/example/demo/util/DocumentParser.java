package com.example.demo.util;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import lombok.Getter;
import com.example.demo.TimeToSecondsConverter;
import com.example.demo.entity.Fho;
import com.example.demo.entity.Details;

public class DocumentParser {
	@Getter
	private Fho fho; 

	@Getter
	private ArrayList<Details> streams; 

	public void parse(List<String> contents) {
		this.clear();
		StringBuilder content = new StringBuilder(256);
		String date = "";
		String startTime = "";
		StringBuilder streamStart = new StringBuilder(256);
		boolean isTitle = false;

		Pattern datePattern = Pattern.compile(".*(\\d{1,2}/\\d{1,2})\\s+(\\d{1,2}:\\d{2}(?::\\d{2})?)\\s*([^\\(]*)(?:\\((\\d{2}:\\d{2})～\\))?(.*)");
		Pattern pattern = Pattern.compile("^\\s*https://(?:www\\.youtube\\.com/(?:live/([^?]+)|watch\\?v=([^&]+)).*|youtu\\.be/(.+))");
		Pattern dPattern = Pattern.compile("^\\s*[^\\(]*?\\d{1,2}:\\d{2}(?::\\d{2})?～\\s*.*");

		int i = 0;
		for (String line: contents){
			Matcher matcherDate = datePattern.matcher(contents.get(i));
			Matcher matcherYouTubeID = pattern.matcher(contents.get(i));
			Matcher matcherd = dPattern.matcher(contents.get(i));
			Matcher matcherAfter = dPattern.matcher("");
			Matcher matcherYoutubeAfter = dPattern.matcher("");
			if(contents.size() > i + 1){
				matcherAfter = dPattern.matcher(contents.get(i + 1));
				matcherYoutubeAfter = pattern.matcher(contents.get(i + 1));
			}
			

			if(matcherYouTubeID.matches()){
				for(int j = 1; j < 4; j++){
					if(Objects.nonNull(matcherYouTubeID.group(j))){
						this.fho.setYoutubeId(matcherYouTubeID.group(j));
					}
				}
			}else{
				if(matcherDate.matches()){
					streamStart.delete(0, streamStart.length());
					streamStart.append(matcherDate.group(1));
					if (Objects.nonNull(matcherDate.group(4))) {
						streamStart.append(" ").append(matcherDate.group(4));
					}
					content.delete(0, content.length());
					if(Objects.nonNull(matcherDate.group(3))){
						content.append(matcherDate.group(3).strip());
					}
					if(Objects.nonNull(matcherDate.group(5))){
						content.append(matcherDate.group(5).strip());
					}
					this.fho.setStreamStart(streamStart.toString());
					this.fho.setTotal(TimeToSecondsConverter.convertToSeconds(matcherDate.group(2)));
					this.fho.setIsMember(0);
					this.fho.setIsDelete(0);

					isTitle = true;
				}else if(matcherd.matches()){
					content.delete(0, content.length()).append(contents.get(i).strip());
					isTitle = false;
				}
			}
			if((matcherYoutubeAfter.matches() || matcherAfter.matches()) && (content.length() > 0 || isTitle)){
				if(isTitle){
					this.fho.setTitle(content.toString().strip());
					isTitle = false;
				}else{
					Details details = this.createDetails(content.toString().strip());
					this.streams.add(details);
				}
				content.delete(0, content.length());
			}else if(!matcherYouTubeID.matches()){
				content.append(" ").append(contents.get(i + 1).strip());
			}
			i++;
		}
	}

	public void clear(){
		this.fho = new Fho();
		this.streams = new ArrayList<Details>();
	}

	private Details createDetails(String line){
		Details details = new Details();
		StringBuilder time = new StringBuilder("00:00:00");
		Pattern dPattern = Pattern.compile("\\s*([^\\(]*?)(\\d{1,2}:\\d{2}(?::\\d{2})?)～\\s*(.*)");
		Matcher matcherd = dPattern.matcher(line);
		StringBuilder info = new StringBuilder();

		if(matcherd.matches()){
			info.append(matcherd.group(1)).append(" ").append(matcherd.group(3));
			String timeString = matcherd.group(2);
			time.replace(time.length() - timeString.length(), time.length(), timeString);
		}else{
			// 不正なフォーマットの場合、原文を返すなどのエラー処理をここに記述します。
		}
		details.setTime(time.toString());
		details.setDescription(info.toString().strip());
		details.setIsDelete(0);

		return details;
	}

}
