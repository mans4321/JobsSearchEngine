package com.mans.JobsSearchEngine.model;

import java.util.HashMap;
import java.util.List;

public class JobScorer {

	private List<String> userSkills;
	
	public JobScorer(List<String> userSkills){
		this.userSkills = userSkills;
	}
	
	public double getScore(String jobDes) {
		HashMap<String, Integer> found = new HashMap<String, Integer>();
		double score = 0;
		
		String[] wordsInJobDes = jobDes.split("\\W+");
		for (String word : wordsInJobDes)
			if (userSkills.contains(word.trim()) && !found.containsKey(word.trim())) {
				score++;
				found.put(word.trim(), 1);
			}
		return score / userSkills.size();
	}


}