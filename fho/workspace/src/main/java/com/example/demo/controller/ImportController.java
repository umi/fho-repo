package com.example.demo.controller;

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

import com.example.demo.entity.Details;
import com.example.demo.entity.Fho;
import com.example.demo.repository.DetailsRepository;
import com.example.demo.service.DetailsService;
import com.example.demo.service.FhoService;
import com.example.demo.util.DocumentParser;

@Controller
public class ImportController {
	@Autowired
    private DetailsRepository detailsRepository;
	
	@Autowired
    private FhoService fhoService;
	
	@Autowired
    private DetailsService detailsService;

    @Autowired
    private PlatformTransactionManager txManager;

	@GetMapping("/insert")
	public String insertFho(Model model) {
		List<String> contents = ReadFileController.readFileContent();
		List<String> data = new ArrayList<>();

		DocumentParser parser = new DocumentParser();
		parser.parse(contents);

		try{
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setName("InsertFho");
			def.setReadOnly(false);
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = txManager.getTransaction(def);

			Fho fho = parser.getFho();
			List<Details> list = parser.getStreams();
			
			//fho_infoにデータINSERT
			fhoService.setData(fho);
			int fhoId = fhoService.lastInsertId();
			data.add(fhoId + " | " + fho.getStreamStart() + " | " + fho.getTitle() + " | " + fho.getYoutubeId());
			
			//stream_infoにデータを挿入
			for(Details details: list){
				detailsService.setData(details, fhoId);
				data.add(details.getTime() + " | " + details.getDescription());
			}

			txManager.commit(status);
		}catch(Exception e){
			data.clear();
			data.add("エラーが発生しました。");
		}

		model.addAttribute("content", data);
		return "read/index"; // or wherever you want to redirect after saving
	}
}
