package com.mans.JobsSearchEngine.model;

import java.util.HashMap;
import java.util.HashSet;

public class Score{
	
	private HashSet<String> kewwordsList ;
	private HashMap<String, Integer> generatedKeywords;
	private int total = 0;
	private int max;
	
	
	public Score(HashSet<String> kewwordsList, HashMap<String, Integer> generatedKeywords){
		this.generatedKeywords = generatedKeywords;
		setTotalAndMax();
		this.kewwordsList = kewwordsList;
	}

	public Score(HashMap<String, Integer> generatedKeywords){
		this.generatedKeywords = generatedKeywords;
		setTotalAndMax();
	}
	public Score(HashSet<String> kewwordsList){
		this.kewwordsList = kewwordsList;
	}
	
	private void setTotalAndMax() {
		for (int v : generatedKeywords.values()){
			if(max < v)
				max = v;
			total += v;
		}
	}

	public double getScore(String jobDes) {
		if(generatedKeywords == null)
			return scoreMethod1(jobDes);
		else
			return scoreMethod2(jobDes);
		
	}

	private double scoreMethod2(String jobDes) {
		double score = 0;
		int m10 = 0 ;
		String[] wordsInJobDes = jobDes.split("\\W+");
		for(String word : wordsInJobDes){
			word = word.trim();
			if(generatedKeywords.containsKey(word)){
				if(kewwordsList != null && kewwordsList.contains(word)){
					score += generatedKeywords.get(word) * 10;
					m10++;
				}
				score += generatedKeywords.get(word);
			}else{
				if(kewwordsList != null && kewwordsList.contains(word)){
					put(word);
				}
					
			}
		}
		return score/(total + (10 * m10));
	}

	private synchronized void put(String word) {
		generatedKeywords.put(word, max *10);
		total += max *10;
	}

	private double scoreMethod1(String jobDes) {
		double score = 0;
		String[] wordsInJobDes = jobDes.split("\\W+");
		for(String word : wordsInJobDes)
			if(kewwordsList.contains(word.trim()))
				score++;
		return score/kewwordsList.size();
	}

}