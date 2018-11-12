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

public class Workopolis extends SearchWebsites {

	public Workopolis(List<String> cities, List<String> searchKeywords, BlockingQueue<JobDescription> queue) {
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
				doc = Jsoup.connect(
						"https://www.workopolis.com/jobsearch/find-jobs?ak=" + jobDes + "&l=" + city + "&lg=en&pn=" + i )
						.get();
			} catch (IOException e1) {
				System.err.println("https://www.workopolis.com/jobsearch/find-jobs?ak=" + jobDes + "&l=" + city
						+ "&lg=en&pn=" + i);
				continue;
			}

			Elements links = doc.select("a.JobCard-titleLink");
			for (Element e : links) {
				String key = e.attr("href");
				key = key.replaceFirst("/jobsearch/viewjob/", "");
				key = key.replace("ak=" + jobDes, "");
				String url = "https://www.workopolis.com/jobsearch/viewjob/" + key;
				if (removeDuplicate.containsKey(key))
					continue;
				removeDuplicate.put(key, 1);
				extract(url);
			}

		}
	}

	protected void extract(String url) {
		String jobDesInHtml = "div.viewjob-description.ViewJob-description";
		String companyNameInHtml = "div.ViewJobHeader-company";
		String jobTitleInHtml = "div.ViewJobHeader-title";
		String cityInHtml = "span.ViewJobHeader-property";
		ii++;
		extract(url, jobDesInHtml, companyNameInHtml, jobTitleInHtml, cityInHtml);

	}
}
