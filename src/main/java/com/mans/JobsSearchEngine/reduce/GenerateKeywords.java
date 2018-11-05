package com.mans.JobsSearchEngine.reduce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mans.JobsSearchEngineI.utility.CleanText;

public class GenerateKeywords {

	
	private GenerateKeywords() {}

	public static ArrayList<String> generate(List<String> jobsDes) {
		HashMap<String, Integer> hash = new HashMap<>();
		ArrayList<String> keywords = new ArrayList<String>();
		
		for(String jobDes : jobsDes){
			jobDes = CleanText.clean(jobDes);
			for(String word : jobDes.split(" ") ){
				if(hash.containsKey(word)){
					hash.put(word, hash.get(word) + 1);
				}else{
					hash.put(word, 1);
				}
			}
		}
		
		hash.forEach((key, value)-> {
			if(value > (jobsDes.size()/2)){
				System.out.println(key +  "  " + value);
				keywords.add(key);
			}
		});
		
		return keywords;
	}



	
	
}
