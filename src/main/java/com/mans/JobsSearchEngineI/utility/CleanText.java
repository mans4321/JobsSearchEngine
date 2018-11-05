package com.mans.JobsSearchEngineI.utility;

import java.util.HashSet;
import java.util.StringTokenizer;

public class CleanText {
	
	

	public static String clean(String text) {

		StringTokenizer tokens = new StringTokenizer(text, " "); // create
																	// tokens
																	// based on
																	// whitespace
		StringBuilder stringBuilder = new StringBuilder();
		while (tokens.hasMoreTokens()) {
	
			String newStr = cleanText(tokens.nextToken()); // clean or normalize

			if (newStr.equalsIgnoreCase("")) // ignore if return is empty after
				continue;
			else
				stringBuilder.append(newStr + " ");
		}

		return stringBuilder.toString().trim();
	}

	public static String simpleCleanText(String text) {
		text = clearSymbols(text);;
		text = caseFold(text); // case fold the token to lowercase
		return text;
	}
	
	private static String cleanText(String text) {
		text = clearSymbols(text);;
		text = caseFold(text); // case fold the token to lowercase
		text = removeStopWords(text);
//		text = stemming(text);
		return text;
	}
	
	private static String clearSymbols(String text){
		return text.replaceAll("[~\\[!#$%^&*(={}.|?><\\.,•)/:'\\]]"," ").trim();
	}

	private static String removeNumbers(String content) {
		return content.replaceAll("[0-9]", "");
	}

	private static String caseFold(String content) {
		return content.toLowerCase();
	}

	private static String removeStopWords(String content) {
		String newContent = content;
		HashSet<String> stopWords = WordList.getInstance().getStopWords();
		for (String stopword : stopWords) {
			if (content.equalsIgnoreCase(stopword))
				newContent = "";
		}
		
		return newContent;
	}

	private static String stemming(String str) {

		Stemmer s = new Stemmer();
		char[] ch = str.toCharArray();
		for (int c = 0; c < ch.length; c++)
			s.add(ch[c]);
		s.stem();
		String stemStr = s.toString();
		return stemStr;
	}

	public static String removeLegalEntityTypes(String companyName) {
		HashSet<String> legalEntityTypes = WordList.getInstance().getLegalEntityTypes();
		for (String type : legalEntityTypes) {
			if (companyName.contains(type))
				companyName = companyName.replace(type, "");;
		}
		return companyName;
	}
}
