package com.mans.JobsSearchEngine.model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageRequest {

	public Document getPsge(String url){
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e1) {
			System.err.println(url);
		}

		return doc;
	}
}
