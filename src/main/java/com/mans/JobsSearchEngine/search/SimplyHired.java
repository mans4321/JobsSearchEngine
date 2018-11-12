package com.mans.JobsSearchEngine.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mans.JobsSearchEngine.model.JobDescription;

public class SimplyHired extends SearchWebsites {

	public SimplyHired(List<String> cities, List<String> searchKeywords, BlockingQueue<JobDescription> queue) {
		super(cities, searchKeywords, queue);
	}

	protected void crawler(String jobDes, String city) {
		jobDes = jobDes.replace(" ", "+");
		Document doc = null;
		HashMap<String, Integer> removeDuplicate = new HashMap<String, Integer>();
		int i = 0;

		for (i = 1; i < 3; i++) {
			ii++;
			try {
				doc = Jsoup.connect("https://www.simplyhired.ca/search?q=" + jobDes + "&l=" + city + "&pn=" + i).get();
			} catch (IOException e1) {
				System.err.println("https://www.simplyhired.ca/search?q=" + jobDes + "&l=" + city + "&pn=" + i);
				continue;
			}

			Elements links = doc.select("a.card-link.js-job-link");
			for (Element e : links) {
				String key = e.attr("href");
				key = key.replaceFirst("/job/", "");
				key = key.replace("q=" + jobDes, "");
				String url = "https://www.simplyhired.ca/job/" + key;
				if (removeDuplicate.containsKey(key))
					continue;
				removeDuplicate.put(key, 1);
				extract(url);
			}

		}
	}

	protected void extract(String url) {
		String jobDesInHtml = "div.viewjob-description";
		String companyNameInHtml = "span.company";
		String jobTitleInHtml = "h1[itemprop=title]";
		String cityInHtml = "span.location";
		ii++;
		extract(url, jobDesInHtml, companyNameInHtml, jobTitleInHtml, cityInHtml);
	}

}
