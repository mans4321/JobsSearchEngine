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

public class Indeed extends SearchWebsites {
	

 
	public Indeed(List<String> cities, List<String> searchKeywords, BlockingQueue<JobDescription> queue) {
		super(cities, searchKeywords, queue);
	}

	protected void crawler(String jobDes, String city) {
		jobDes = jobDes.replace(" ", "+");
		Document doc = null;
		HashMap<String, Integer> removeDuplicate = new HashMap<String, Integer>();
		int i = 0;
		for (i = 0; i < 40; i += 20) {
			ii++;
			try {
				doc = Jsoup.connect("https://www.indeed.ca/jobs?q=" + jobDes + "&l=" + city + "&start=" + i).get();
			} catch (IOException e1) {
				System.err.println("https://www.indeed.ca/jobs?q=" + jobDes + "&l=" + city + "&start=" + i);
				continue;
			}

			Elements links = doc.select("a");
			for (Element e : links) {
				int index = e.attr("onmousedown").indexOf("fromjk");
				String jobKey;
				if (index != -1) {
					String process = e.attr("onmousedown");
					jobKey = process.substring(index, process.indexOf('&', index)).split("=")[1].trim();
					String url = "https://ca.indeed.com/viewjob?jk=" + jobKey;
					if (removeDuplicate.containsKey(jobKey))
						continue;
					removeDuplicate.put(jobKey, 1);
					extract(url);
				}
			}
		}
	}

	protected void extract(String url) {
		String jobDesInHtml = "div.jobsearch-JobComponent-description";
		String companyNameInHtml = "div.icl-u-lg-mr--sm.icl-u-xs-mr--xs";
		String jobTitleInHtml = "[class$='jobsearch-JobInfoHeader-title']";
		String cityInHtml = "div.jobsearch-InlineCompanyRating > div:not(.icl-u-lg-mr--sm)";
		ii++;
		extract(url, jobDesInHtml, companyNameInHtml, jobTitleInHtml, cityInHtml);
	}

}
