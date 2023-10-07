package com.example.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ファイルの分割読み込み
 */
public class DocumentDivider implements Iterator<List<String>> {

	private String path;
	private long currentPointer;
	private long nextPointer;
	private ArrayList<String> lineparts;
	private Pattern headPattern;
	private Pattern urlPattern;
	private RandomAccessFile reader;
	private Charset charset;

	public DocumentDivider() {
		headPattern = DocumentParser.getHeadLinePattern();
		urlPattern = DocumentParser.getYoutubePattern();
		charset = StandardCharsets.UTF_8;
		lineparts = new ArrayList<>();
	}

	/**
	 * @param String path
	 * @return void
	 */
	public void setPath(String documentPath){
        path = documentPath;
		this.reset();
	}

	public void reset(){
		currentPointer = 0;
	}

	public long read(long pointer) throws FileNotFoundException, IOException {
		if (pointer < 0) {
			lineparts.clear();
			return -1;
		}
		boolean start = false;
		boolean matched = false;
		long nextReturn;
		long prevPointer = -1;
		String line;
		byte[] bytes;
		try{
			reader = new RandomAccessFile(new File(path), "r");
			reader.seek(pointer);
            while (true) {
                line = reader.readLine();
                nextReturn = reader.getFilePointer();
                if (line == null) {
                    break;
                }
                {
                    reader.seek(pointer);
                    bytes = new byte[(int) (nextReturn - pointer)];
                    reader.read(bytes);
                    pointer = reader.getFilePointer();
                    line = new String(bytes, charset);
                    while (line.endsWith("\r") || line.endsWith("\n")) {
                        line = line.substring(0, line.length() - 1);
                    }
                }

				if (!start) {
					if(headPattern.matcher(line).matches()){
						start = true;
					}else{
						continue;
					}
				}else{
					if (urlPattern.matcher(line).matches()) {
						matched = true;
					} else if (matched) {
						break;
					}
				}
				lineparts.add(line);
				nextPointer = prevPointer = pointer;
            }
            reader.close();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e){
				}
			}
		}

		if(matched){
			return prevPointer;
		}else{
			lineparts.clear();
			return -1;
		}
	}

	public boolean hasNext() {
		if(currentPointer < 0){
			return false;
		}
		long res = 0;
		try{
			if(lineparts.isEmpty()){
				res = this.read(currentPointer);
			}
		}catch(Exception e){
			return false;
		}

		return res >= 0 && !lineparts.isEmpty();
	}

	public List<String> next() {
		try{
			if(lineparts.isEmpty()){
				this.read(currentPointer);
			}
			currentPointer = nextPointer;
			if(currentPointer < 0){
				lineparts.clear();
				return null;
			}
		}catch(Exception e){
			lineparts.clear();
			return null;
		}

		ArrayList<String> contents = (ArrayList<String>) lineparts.clone();
		lineparts.clear();

		return contents;
	}
}
