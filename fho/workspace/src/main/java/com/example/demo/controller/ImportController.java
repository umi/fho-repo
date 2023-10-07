package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Fho;
import com.example.demo.entity.Stream;
import com.example.demo.repository.MarkRepository;
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
    private StreamMarkService StreamMarkService;
	
	@Autowired
	private MarkRepository markRepository;

    @Autowired
    private PlatformTransactionManager txManager;
    
	@Autowired
	DocumentParser parser = new DocumentParser();

	@GetMapping("/insert")
	public String insert(@RequestParam String year, Model model) {
		DocumentDivider documentDivider = new DocumentDivider();
		documentDivider.setPath("src/main/resources/upload/sample.txt");

		List<String> data = new ArrayList<>();
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
		return "read/index"; // or wherever you want to redirect after saving
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
			List<Stream> list = parser.getStreams();

			
			//fho_infoにデータINSERT
			fhoService.setData(fho);
			int fhoId = fhoService.lastInsertId();
			data.add(fhoId + " | " + fho.getStreamStart() + " | " + fho.getTitle() + " | " + fho.getYoutubeId());
			
			//stream_infoにデータを挿入
			for(Stream stream: list){
				streamService.setData(stream, fhoId);
				int streamId = streamService.lastInsertId();
				Integer id[][] = parser.getId();
				
				//stream_markテーブルに紐づけを格納
				for(Integer markIdInteger: id[i]) {
					markId = markIdInteger == null ? 0: markIdInteger;
					if(markId > 0) {
						StreamMarkService.setData(streamId, markId);
					}
				}
				
				markId = 0;
				i++;
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
