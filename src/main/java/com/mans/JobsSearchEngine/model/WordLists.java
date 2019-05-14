package com.mans.JobsSearchEngine.model;

import java.util.ArrayList;
import java.util.List;

public class WordLists {

	private List<String> stopWords;
	private List<String> legalEntityTypes;
	
	public WordLists(){
		stopWords = new ArrayList<>();
		legalEntityTypes = new ArrayList<>();
	}
	
	public List<String> getStopWords() {
		return stopWords;
	}
	
	public void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}
	
	public List<String> getLegalEntityTypes() {
		return legalEntityTypes;
	}

	public void setLegalEntityTypes(List<String> legalEntityTypes) {
		this.legalEntityTypes = legalEntityTypes;
	}
}
