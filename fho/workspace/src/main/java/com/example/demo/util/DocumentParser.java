package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.TimeToSecondsConverter;
import com.example.demo.entity.Fho;
import com.example.demo.entity.Stream;
import com.example.demo.service.MarkService;

import lombok.Getter;

/**
 * メモデータのパース
 */
@Service
public class DocumentParser {
	@Getter
	private Fho fho;

	@Getter
	private ArrayList<Stream> streams;
	
	@Getter
	private Integer id[][];
	
	@Autowired
    private MarkService markService;
	

	/**
	 * メモデータ1件分からデータ抽出してメンバー変数へ格納
	 * @param List<String> contents
	 * @return void
	 */
	public void parse(List<String> contents) {
		this.clear();
		StringBuilder content = new StringBuilder(256);
		StringBuilder streamStart = new StringBuilder(256);
		boolean isTitleInserted = false;

		// 日付 タイトル 時間
		Pattern headPattern = this.getHeadLinePattern();
		// stream line
		Pattern streamPattern = this.getStreamLinePattern();
		// url
		Pattern urlPattern = this.getYoutubePattern();

		int i = 0;
		int k = 0;
		for (String line: contents){
			Matcher matcherHead = headPattern.matcher(line);
			Matcher matcherYouTubeID = urlPattern.matcher(line);
			Matcher matcherStream = streamPattern.matcher(line);
			Matcher matcherStreamAfter = streamPattern.matcher("");
			Matcher matcherYoutubeAfter = urlPattern.matcher("");
			//次の行の形式確認用
			if(contents.size() > i + 1){
				matcherStreamAfter = streamPattern.matcher(contents.get(i + 1));
				matcherYoutubeAfter = urlPattern.matcher(contents.get(i + 1));
			}
			
			//URLパターンにマッチした場合
			if(matcherYouTubeID.matches()){
				//YouTubeID 3パターンのいずれかを取り込む
				for(int j = 0; j < 3; j++){
					if(Objects.nonNull(matcherYouTubeID.group(j))){
						fho.setYoutubeId(matcherYouTubeID.group(j));
						break;
					}
				}
			}else{
				//日付・タイトル・総配信時間行にマッチした場合
				if(!isTitleInserted && matcherHead.matches()){
					// fho_infoの開始日時
					streamStart.delete(0, streamStart.length());
					streamStart.append(matcherHead.group(1));
					if (Objects.nonNull(matcherHead.group(4))) {
						streamStart.append(" ").append(matcherHead.group(4));
					}
					//タイトルの作成
					content.delete(0, content.length());
					if(Objects.nonNull(matcherHead.group(3))){
						content.append(matcherHead.group(3).strip());
					}
					if(Objects.nonNull(matcherHead.group(5))){
						content.append(matcherHead.group(5).strip());
					}
					fho.setStreamStart(streamStart.toString());
					fho.setTotal(TimeToSecondsConverter.convertToSeconds(matcherHead.group(2)));
					fho.setIsMember(0);
					fho.setIsDelete(0);
					
				//内容の行にマッチした場合
				}else if(matcherStream.matches()){
					content.delete(0, content.length()).append(line.strip());
				}
			}
			//（次が内容行の形式かURLの場合） かつ （contentにデータが格納されている、または、タイトルフラグが立っている場合）
			if((matcherYoutubeAfter.matches() || matcherStreamAfter.matches()) && (content.length() > 0 || !isTitleInserted)){
				if(!isTitleInserted){
					//タイトルを格納
					fho.setTitle(content.toString().strip());
					isTitleInserted = true;
					
				}else{
					
					List<String> lineparts = this.createStream(content.toString().strip());
					Stream stream = new Stream();
					
					List<String> emojis = new ArrayList<>();
					emojis = DocumentEmojiExtractor.extractEmojis(lineparts.get(0));	//全文から絵文字のみ抽出
					
					for(int j = 0; j<emojis.size();j++) {
							id[k][j]=markService.markToId(emojis.get(j)); //絵文字IDをListに格納
					}
					
					stream.setTime(lineparts.get(1));
					stream.setDescription(lineparts.get(2));
					stream.setIsDelete(0);
					//stream_infoに格納するデータset
					streams.add(stream);
					k++;
				}
				content.delete(0, content.length());
			}else if(!matcherYouTubeID.matches()){
				content.append(" ").append(contents.get(i + 1).strip());
			}
			i++;
		}
	}

	/**
	 * メンバー変数の初期化
	 * @return void
	 */
	public void clear(){
		fho = new Fho();
		streams = new ArrayList<Stream>();
		id = new Integer[1000][1000];
	}

	private List<String> createStream(String line){
		List<String> lineparts = new ArrayList<>();
		StringBuilder time = new StringBuilder("00:00:00");
		Pattern streamPattern = this.getStreamLinePattern();
		Matcher matcherStream = streamPattern.matcher(line);
		
		//（時間の前にマークがあってもOK）内容の行とマッチした場合
		if(matcherStream.matches()){
			lineparts.add(matcherStream.group(0));	//全文格納
			String timeString = matcherStream.group(2);
			time.replace(time.length() - timeString.length(), time.length(), timeString);
		}else{
			// 不正なフォーマットの場合、原文を返すなどのエラー処理をここに記述します。
		}
		//チェックポイント時間と内容を格納
		lineparts.add(time.toString());
		lineparts.add(matcherStream.group(3));

		return lineparts;
	}

	/**
	 * 👑9/1    3:12:23    少し(22:42～)
	 * のようなパターンの検出 末尾に文字が入った場合も対応
	 * @return Pattern
	 */
	public static Pattern getHeadLinePattern() {
		return Pattern.compile(".*(\\d{1,2}/\\d{1,2})\\s+(\\d{1,2}:\\d{2}(?::\\d{2})?)\\s*([^\\(]*)(?:\\((\\d{1,2}:\\d{2})～\\))?(.*)");
	}

	/**
	 * 1:17:36～結構首都覚えましたか？ アフリカばっかで…
	 * のようなパターンの検出
	 * @return Pattern
	 */
	public static Pattern getStreamLinePattern() {
		return Pattern.compile("^\\s*([^※\\(]*?)(\\d{1,2}:\\d{2}(?::\\d{2})?)～\\s*(.*)");
	}

	/**
	 * https://www.youtube.com/live/0gl5HYfZimw?si=P3nMxUhf1s2BpA5n 
	 * https://www.youtube.com/watch?v=0gl5HYfZimw&ab_channel=
	 * https://youtu.be/0gl5HYfZimw
	 * のようなパターンの検出(3パターン)
	 * @return Pattern
	 */
	public static Pattern getYoutubePattern() {
		return Pattern.compile("^\\s*https://(?:www\\.youtube\\.com/(?:live/([^?]+)|watch\\?v=([^&]+)).*|youtu\\.be/(.+))");
	}

}
