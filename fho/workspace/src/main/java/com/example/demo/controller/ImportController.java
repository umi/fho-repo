package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.TransactionDefinition;

import com.example.demo.entity.Fho;
import com.example.demo.entity.Details;
import com.example.demo.service.FhoService;
import com.example.demo.service.DetailsService;
import com.example.demo.repository.DetailsRepository;
import com.example.demo.controller.ReadFileController;

import com.example.demo.TimeToSecondsConverter;
import com.example.demo.util.DocumentParser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
			fhoService.setData(fho);
			int fhoId = fhoService.lastInsertId();
			data.add(fhoId + " | " + fho.getStreamStart() + " | " + fho.getTitle() + " | " + fho.getYoutubeId());
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
