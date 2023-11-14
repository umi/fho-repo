package com.example.demo.controller;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.DescriptionDTO;
import com.example.demo.entity.Battle;
import com.example.demo.entity.Fho;
import com.example.demo.entity.Stream;
import com.example.demo.repository.MarkRepository;
import com.example.demo.service.BattleService;
import com.example.demo.service.FhoService;
import com.example.demo.service.StreamMarkService;
import com.example.demo.service.StreamService;
import com.example.demo.util.DocumentDivider;
import com.example.demo.util.DocumentParser;

@Controller
public class ImportController {
	@Autowired
    private FhoService fhoService;
	
	@Autowired
    private StreamService streamService;
	
	@Autowired
    private StreamMarkService streamMarkService;
	
	@Autowired
    private BattleService battleService;
	
	@Autowired
	private MarkRepository markRepository;

    @Autowired
    private PlatformTransactionManager txManager;
    
	@Autowired
	DocumentParser parser = new DocumentParser();
	
	String destinationPath;

	@PostMapping("/insert")
	public String insert(@RequestParam("file") MultipartFile file,@RequestParam String year, Model model) {
		DocumentDivider documentDivider = new DocumentDivider();
		try {
            // 例: ファイルをサーバー上の特定の場所に保存
            destinationPath = "D:/Git/fho-repo/fho/workspace/src/main/resources/upload/" + file.getOriginalFilename();
            file.transferTo(new File(destinationPath));
            
        } catch (Exception e) {
            return "ファイルアップロード失敗: " + e.getMessage();
        }
		
		documentDivider.setPath(destinationPath);

		List<String> data = new ArrayList<>();
		data.add(destinationPath);
		data.add("=========================================================");
		int i = 0;
		while(documentDivider.hasNext()){
			List<String> contents = documentDivider.next();
			data.addAll(this.insertFho(contents, year));
			data.add("=========================================================");
			if(i > 1000){
				break;
			}
			i++;
		}

		model.addAttribute("content", data);
		model.addAttribute("year", year);
		
		return "read/index"; // or wherever you want to redirect after saving
	}
	
	@GetMapping("/upload")
	public String upload(Model model) {
		List<DescriptionDTO> descriptions = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			descriptions.add(new DescriptionDTO("",""));
			}
		
		model.addAttribute("descriptions", descriptions);
		
		return "upload/index";
	}
	
	@GetMapping("/uploadData")
	public String uploadData(Model model) {
		return "upload/index";
	}

	private List<String> insertFho(List<String> contents, String year) {
		List<String> data = new ArrayList<>();
		int markId = 0;
		int i = 0;

		try {
			parser.parse(contents, year);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		try{
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setName("InsertFho");
			def.setReadOnly(false);
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = txManager.getTransaction(def);

			Fho fho = parser.getFho();
			List<Battle> battles = parser.getBattles();
			List<Stream> list = parser.getStreams();
			List<Integer> youtubeIds = fhoService.getYouTubeToId(fho.getYoutubeId());
			
			//fho_infoに存在するyoutubeIdが重複するデータを削除
			if(youtubeIds != null && !youtubeIds.isEmpty()) {
				for(int youtubeId : youtubeIds) {
					fhoService.deleteData(youtubeId);
				}
			}
			
			//fho_infoにデータINSERT
			fhoService.setData(fho);
			int fhoId = fhoService.lastInsertId();
			data.add(fhoId + " | " + fho.getStreamStart() + " | " + fho.getTitle() + " | " + fho.getYoutubeId());
			
			for(Stream stream: list){
				//stream_infoにデータを挿入
				streamService.setData(stream, fhoId);
				int streamId = streamService.lastInsertId();
				Integer id[][] = parser.getMarkId();
				
				//stream_markテーブルに紐づけを格納
				for(Integer markIdInteger: id[i]) {
					markId = markIdInteger == null ? 0: markIdInteger;
					if(markId > 0) {
						streamMarkService.setData(streamId, markId);
					}
				}
				
				//1v1テーブルに対戦記録を格納
				Battle battle = battles.get(i);
				if(battle.getCreativeId() > 0 && battle.getOpponentId() > 0) {
					battle.setId(streamId);
					battleService.setData(battle);
				}
				
				markId = 0;
				i++; //取り込み行のインクリメント 開始は0
				data.add(stream.getTime() + " | " + stream.getDescription());
			}
			
			
			
			txManager.commit(status);
		}catch(Exception e){
			data.clear();
			data.add("エラーが発生しました。");
		}

		return data;
	}
}
