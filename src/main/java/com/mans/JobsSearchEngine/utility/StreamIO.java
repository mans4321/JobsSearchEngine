package com.mans.JobsSearchEngine.utility;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mans.JobsSearchEngine.model.WordLists;
import com.mans.JobsSearchEngine.model.job.JobSearchInfo;

public class StreamIO {

	private final String USER_HOME = System.getProperty("user.home") + File.separator;
	
	private WordLists wordLists;
	private JobSearchInfo searchInfo;
	private static StreamIO streamIO;

	public static StreamIO getInstance() {
		if (streamIO == null)
			streamIO = new StreamIO();
		return streamIO;
	}
	
	private StreamIO() {
		searchInfo = getLatestSearchInfoFromFile("userInfo.json");
		wordLists = getWordListFromFile("wordLists.json");
	}

	private WordLists getWordListFromFile(String fileName) {
		String fileLocation = getClass().getResource("/" + fileName).getPath();
		WordLists wordLists = readFile(fileLocation, WordLists.class);
		return Objects.nonNull(wordLists) ? wordLists : new WordLists();
	}

	private JobSearchInfo getLatestSearchInfoFromFile(String fileName) {
		String fileLocation = USER_HOME + fileName;
		JobSearchInfo userInfo = readFile(fileLocation, JobSearchInfo.class);
		return Objects.nonNull(userInfo) ? userInfo : new JobSearchInfo();
	}
	
	private <T> T readFile(String fileLocation, Class<T> type){
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.readValue(new File(fileLocation), type);
		}catch(Exception e) {
			return null;
		}
	}
	
	public void writeToFile(JobSearchInfo userInfo) {
		String fileLocation = USER_HOME + "userInfo.json";
		writeToFile(fileLocation, userInfo);
	}

	private <T> void writeToFile(String fileLocation, T info){
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(new File(fileLocation), info);
		} catch (IOException e) {
			System.err.println("cannot write to " + fileLocation);
		}
	}
	
	public WordLists getWordLists() {
		return wordLists;
	}

	public JobSearchInfo getUserInfo() {
		return searchInfo;
	}
}
