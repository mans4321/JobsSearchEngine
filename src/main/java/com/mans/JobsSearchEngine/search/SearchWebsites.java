package com.mans.JobsSearchEngine.search;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.mans.JobsSearchEngine.model.JobDescription;
import com.mans.JobsSearchEngine.utility.CleanText;

public abstract class SearchWebsites {

	protected BlockingQueue<JobDescription> queue;
	protected List<String> cities;
	protected List<String> searchKeywords;
	private String htmlHeader;
	private String htmlFooter;

	public SearchWebsites(List<String> cities, List<String> searchKeywords, BlockingQueue<JobDescription> queue) {

		this.cities = cities;
		this.queue = queue;
		this.searchKeywords = searchKeywords;
		htmlHeader = "<!DOCTYPE html><html><head><title></title></head><body>";
		htmlFooter = "</body></html>";
	}

	protected abstract void crawler(String keyword, String city);

	protected abstract void extract(String url);

	public void search() {
		for (String city : cities) {
			for (String keyword : searchKeywords) {
				crawler(keyword, city);
			}

		}
		JobDescription fake = new JobDescription(null, null, null, null, null, null);
		queue.add(fake);
	}

	protected void extract(String url, String jobDesInHtml, String companyNameInHtml, String jobTitleInHtml,
			String cityInHtml) {
		try {
			Document doc = null;
			doc = Jsoup.connect(url).get();
			Element htmlJobDes = doc.selectFirst(jobDesInHtml);
			String jobDes = htmlJobDes.text();
			jobDes = CleanText.clean(jobDes);

			String companyName = doc.selectFirst(companyNameInHtml).text();
			companyName = CleanText.simpleCleanText(companyName);
			companyName = CleanText.removeLegalEntityTypes(companyName);

			String jobTitle = doc.selectFirst(jobTitleInHtml).text();
			jobTitle = CleanText.simpleCleanText(jobTitle);

			String city = doc.selectFirst(cityInHtml).text();
			if (city != null && city.contains(","))
				city = city.split(",")[0];

			JobDescription jobDescription = new JobDescription(jobTitle, companyName, city, jobDes, url,
					htmlHeader + htmlJobDes.toString() + htmlFooter);
			queue.add(jobDescription);
		} catch (Exception e) {
			System.out.println("something went wrong while parsing " + url);
		}

	}
}
