package com.mans.JobsSearchEngine.utility;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class WordList {

	private HashSet<String> stopWords;
	private HashSet<String> jobKeywords;
	private HashSet<String> legalEntityTypes;
	private HashSet<String> rememberMeJobsTitle;
	private HashSet<String> rememberMeJobsLocation;
	private HashSet<String> rememberMeExperience;
	private HashSet<String> jobSkils;
	
	private static WordList wordList;

	private WordList() {
		rememberMeJobsTitle = getWordsFromFile("rememberMeJobsTitle.txt");
		rememberMeExperience = getWordsFromFile("rememberMeExperience.txt");
		stopWords = getWordsFromFile("longStopWords.txt");
		jobKeywords = getWordsFromFile("jobKeywords.txt");
		jobSkils		= getJobSkils();
		legalEntityTypes = getWordsFromFile("legalEntityTypes.txt");
		rememberMeJobsLocation = getWordsFromFile("rememberMeJobsLocation.txt");
		
	}


	private HashSet<String> getWordsFromFile(String fileName) {
		
		HashSet<String> list = new HashSet<String>();
		String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(System.getProperty("user.home")+ File.separator +fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	if (!line.trim().equals("")){
            		list.add(line.trim());
            	}
				
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");   
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
        return list;
    }

		

	public void writeToFile(List<String> skils, List<String> jobTitles, List<String> locations, String experience) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String skil : skils) {
			stringBuilder.append(skil);
			stringBuilder.append("\n");
		}
		if (stringBuilder.length() > 0)
			write("jobKeywords.txt", stringBuilder.toString());

		stringBuilder = new StringBuilder();
		for (String str : jobTitles) {
			stringBuilder.append(str);
			stringBuilder.append("\n");
		}
		if (stringBuilder.length() > 0)
			write("rememberMeJobsTitle.txt", stringBuilder.toString());

		stringBuilder = new StringBuilder();
		for (String str : locations) {
			stringBuilder.append(str);
			stringBuilder.append("\n");
		}
		if (stringBuilder.length() > 0)
			write("rememberMeJobsLocation.txt", stringBuilder.toString());

		stringBuilder = new StringBuilder();
		if (experience.trim().length() > 0) {
			stringBuilder.append(experience.trim());
			stringBuilder.append("\n");
			write("rememberMeExperience.txt", stringBuilder.toString());
		}
	}

	private void write(String fileName, String str) {
		try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(System.getProperty("user.home")+ File.separator +fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(str);

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
	}

	public HashSet<String> getRememberMeJobsTitle() {
		return rememberMeJobsTitle;
	}

	public HashSet<String> getRememberMeJobsLocation() {
		return rememberMeJobsLocation;
	}

	public static WordList getInstance() {
		if (wordList == null)
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
	
	public HashSet<String> getJobSkils() {
		HashSet<String> skils = new HashSet<String>();
		for(String skil : jobKeywords)
		{
			if(skil.contains(" ")){
				for(String subSkill : skil.split(" ")){
					skils.add(subSkill.trim());
				}
			}else{
				skils.add(skil.trim());
			}
				
		}
		return skils;
	}

	public String getRememberMeExperience() {
		String experience = "";
		if (rememberMeExperience.size() > 0)
			return experience = (String) rememberMeExperience.toArray()[0];
		return experience;
	}

}
