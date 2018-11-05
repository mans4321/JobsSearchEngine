package com.mans.JobsSearchEngineI.utility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class WordList {

	private HashSet<String> stopWords;
	private HashSet<String> jobKeywords ;
	private HashSet<String> legalEntityTypes ;
	private HashSet<String> rememberMeJobsTitle;
	private HashSet<String> rememberMeJobsLocation;
	private HashSet<String>  rememberMeExperience;
	private static WordList wordList;
	
	private WordList(){
		stopWords 		 		= getWordsFromFile("longStopWords.txt");
		jobKeywords		 		= getWordsFromFile("jobKeywords.txt");
		legalEntityTypes 		= getWordsFromFile("legalEntityTypes.txt");
		rememberMeJobsTitle		= getWordsFromFile("rememberMeJobsTitle.txt");
		rememberMeJobsLocation	= getWordsFromFile("rememberMeJobsLocation.txt");
		rememberMeExperience 	= getWordsFromFile("rememberMeExperience.txt");
	}
	
	private  HashSet<String> getWordsFromFile (String fileName)
	{
		HashSet<String> list = new HashSet<String>();

		File file = new File(System.getProperty("user.dir") + "/res/textFiles/"+fileName);
		FileReader reader = null;

		try {
			reader = new FileReader(file);
		
		char[] chars = new char[(int) file.length()];
		reader.read(chars);
		reader.close();
		
		String content = new String(chars);
		StringTokenizer stopWords = new StringTokenizer(content);
		while(stopWords.hasMoreTokens())
		{
			list.add(stopWords.nextToken().toLowerCase());
		}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return list;
		}
		
		return list;

	}
	

	public void writeToFile(String skils, List<String>jobTitles, List<String> locations, String experience){
		StringBuilder stringBuilder = new StringBuilder();
		skils = skils.trim();
		for(String skil : skils.split(" ")){
			stringBuilder.append(skil);
			stringBuilder.append("\n");
		}
		if(stringBuilder.length() > 0)
			write("jobKeywords.txt", stringBuilder.toString());
		
		stringBuilder = new StringBuilder();
		for(String str : jobTitles){
			stringBuilder.append(str);
			stringBuilder.append("\n");
		}
		if(stringBuilder.length() > 0)
			write("rememberMeJobsTitle.txt", stringBuilder.toString());
		
		stringBuilder = new StringBuilder();
		for(String str : locations){
			stringBuilder.append(str);
			stringBuilder.append("\n");
		}
		if(stringBuilder.length() > 0)
			write("rememberMeJobsLocation.txt", stringBuilder.toString());
		
		stringBuilder = new StringBuilder();
		if(experience.trim().length() > 0){
			stringBuilder.append(experience.trim());
			stringBuilder.append("\n");
			write("rememberMeExperience.txt", stringBuilder.toString());
		}
	}

	private void write(String fileName, String str){
        File file = new File(System.getProperty("user.dir") + "/res/textFiles/" + fileName);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	
	public HashSet<String> getRememberMeJobsTitle() {
		return rememberMeJobsTitle;
	}

	public HashSet<String> getRememberMeJobsLocation() {
		return rememberMeJobsLocation;
	}

	public static  WordList getInstance(){
		if(wordList == null)
			wordList = new WordList();
		return wordList;
	}

	public HashSet<String> getStopWords() {
		return stopWords;
	}

	public HashSet<String> getJobKeywords() {
		return jobKeywords;
	}

	public HashSet<String> getLegalEntityTypes() {
		return legalEntityTypes;
	}

	public String getRememberMeExperience() {
		String experience = "";
		if(rememberMeExperience.size() > 0)
			return experience = (String) rememberMeExperience.toArray()[0] ;
		return experience;
	}

}
