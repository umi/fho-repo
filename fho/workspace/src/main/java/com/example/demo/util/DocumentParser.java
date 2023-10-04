package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.TimeToSecondsConverter;
import com.example.demo.entity.Details;
import com.example.demo.entity.Fho;

import lombok.Getter;

public class DocumentParser {
	@Getter
	private Fho fho;

	@Getter
	private ArrayList<Details> streams;
	
	@Getter
	private ArrayList<String> smark;

	public void parse(List<String> contents) {
		this.clear();
		StringBuilder content = new StringBuilder(256);
		StringBuilder streamStart = new StringBuilder(256);
		boolean isTitle = false;

		// 👑9/1    3:12:23    少し(22:42～) のようなパターンの検出　末尾に文字が入った場合も対応　
		Pattern datePattern = Pattern.compile(".*(\\d{1,2}/\\d{1,2})\\s+(\\d{1,2}:\\d{2}(?::\\d{2})?)\\s*([^\\(]*)(?:\\((\\d{2}:\\d{2})～\\))?(.*)");
		
		// https://www.youtube.com/live/0gl5HYfZimw?si=P3nMxUhf1s2BpA5n 
		// https://youtu.be/xi8L0gWq9hM                                     のようなパターンの検出(3パターン)
		Pattern pattern = Pattern.compile("^\\s*https://(?:www\\.youtube\\.com/(?:live/([^?]+)|watch\\?v=([^&]+)).*|youtu\\.be/(.+))");
		
		// 1:17:36～結構首都覚えましたか？ アフリカばっかで… のようなパターンの検出
		Pattern dPattern = Pattern.compile("^\\s*[^\\(]*?\\d{1,2}:\\d{2}(?::\\d{2})?～\\s*.*");

		int i = 0;
		for (String line: contents){
			Matcher matcherDate = datePattern.matcher(line);
			Matcher matcherYouTubeID = pattern.matcher(line);
			Matcher matcherd = dPattern.matcher(line);
			Matcher matcherAfter = dPattern.matcher("");
			Matcher matcherYoutubeAfter = dPattern.matcher("");
			//次の行の形式確認用
			if(contents.size() > i + 1){
				matcherAfter = dPattern.matcher(contents.get(i + 1));
				matcherYoutubeAfter = pattern.matcher(contents.get(i + 1));
			}
			
			//URLパターンにマッチした場合
			if(matcherYouTubeID.matches()){
				//YouTubeID 3パターンのいずれかを取り込む
				for(int j = 1; j < 4; j++){
					if(Objects.nonNull(matcherYouTubeID.group(j))){
						this.fho.setYoutubeId(matcherYouTubeID.group(j));
					}
				}
			}else{
				//日付・タイトル・総配信時間行にマッチした場合
				if(matcherDate.matches()){
					streamStart.delete(0, streamStart.length());
					// fho_infoの開始日時
					streamStart.append(matcherDate.group(1));
					if (Objects.nonNull(matcherDate.group(4))) {
						streamStart.append(" ").append(matcherDate.group(4));
					}
					content.delete(0, content.length());
					//タイトルの作成
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
					
				//内容の行にマッチした場合
				}else if(matcherd.matches()){
					content.delete(0, content.length()).append(line.strip());
					isTitle = false;
				}
			}
			//（次が内容行の形式かURLの場合） かつ （contentにデータが格納されている、または、タイトルフラグが立っている場合）
			if((matcherYoutubeAfter.matches() || matcherAfter.matches()) && (content.length() > 0 || isTitle)){
				if(isTitle){
					//タイトルを格納
					this.fho.setTitle(content.toString().strip());
					isTitle = false;
				}else{
					List<String> lineparts = this.createDetails(content.toString().strip());
					Details details = new Details();
					
					//streamMarkに格納するためのマークをset
					this.smark.add(lineparts.get(0));
					
					details.setTime(lineparts.get(1));
					details.setDescription(lineparts.get(2));
					details.setIsDelete(0);
					//stream_infoに格納するデータset
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
		this.smark = new ArrayList<String>();
	}

	private List<String> createDetails(String line){
		List<String> lineparts = new ArrayList<>();
		StringBuilder time = new StringBuilder("00:00:00");
		Pattern dPattern = Pattern.compile("\\s*([^\\(]*?)(\\d{1,2}:\\d{2}(?::\\d{2})?)～\\s*(.*)");
		Matcher matcherd = dPattern.matcher(line);
		
		//（時間の前にマークがあってもOK）内容の行とマッチした場合
		if(matcherd.matches()){
			//マークの格納（時間の前にあるマーク）
			lineparts.add(matcherd.group(1));
			String timeString = matcherd.group(2);
			time.replace(time.length() - timeString.length(), time.length(), timeString);
		}else{
			// 不正なフォーマットの場合、原文を返すなどのエラー処理をここに記述します。
		}
		//stream_infoにチェックポイント時間と内容を格納
		lineparts.add(time.toString());
		lineparts.add(matcherd.group(3));

		return lineparts;
	}

}
