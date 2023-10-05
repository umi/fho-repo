package com.example.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;





/**
 * メモデータの分割
 */
public class DocumentDivider implements Iterator {

	private Pattern headPattern;
	private Pattern urlPattern;
	private ClassPathResource resource;
	private RandomAccessFile reader;
	private Charset charset;

	public DocumentDivider() {
		DocumentParser documentParser = new DocumentParser();
		headPattern = documentParser.getHeadLinePattern();
		urlPattern = documentParser.getYoutubePattern();
		charset = StandardCharsets.UTF_8;
	}

	/**
	 * ファイルの読み込み
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param String path
	 * @return void
	 */
	public void open(String path) throws FileNotFoundException, IOException {
        resource = new ClassPathResource(path);
		reader = new RandomAccessFile(new File(path), "r");
	}

	public void close() throws IOException {
		reader.close();
	}

	public boolean hasNext() {
		return true;
	}

	public Object next() {
		return "";
	}
}
