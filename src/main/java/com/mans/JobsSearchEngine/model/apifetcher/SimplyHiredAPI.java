package com.mans.JobsSearchEngine.model.apifetcher;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mans.JobsSearchEngine.model.job.JobDescription;
import com.mans.JobsSearchEngine.model.job.JobInfoExtractor;
import com.mans.JobsSearchEngine.model.job.JobInfoSelectors;

public class SimplyHiredAPI implements FetchJobsAPI {

	private JobInfoExtractor jobInfoExtractor;

	public SimplyHiredAPI(){
		jobInfoExtractor = new JobInfoExtractor();
	}
	
	@Override
	public List<String> getResultPagesURL(String jobDes, String city, int numberOfPages) {
		List<String> urls = new ArrayList<>();
		int page = 0;
		while (page < numberOfPages) {
			urls.add(constructPageUrl(jobDes, city, page));
			page++;
		}
		return urls;
	}

	@Override
	public List<String> getJobsUrlsOnPage(Document page) {
		List<String> urls = new ArrayList<>();
		
		Elements links = page.select("a.card-link.js-job-link");
		for (Element e : links) {
			String jobDeskey = e.attr("href");
			jobDeskey = jobDeskey.replaceFirst("/job/", "");
			jobDeskey = jobDeskey.replace("q=" + jobDeskey, "");

			final String jobDesLink = "https://www.simplyhired.ca/job/" + jobDeskey;
			urls.add(jobDesLink);
		}
		return urls;
	}

	@Override
	public JobDescription extractJobDescriptionOnPage(Document page) {
		JobInfoSelectors selectors = new JobInfoSelectors();
		selectors.setJobDes("div.viewjob-description");
		selectors.setCompanyName("span.company");
		selectors.setJobTitle("h1[itemprop=title]");
		selectors.setCity("span.location");
		
		JobDescription jobDes = jobInfoExtractor.extract(page, selectors);
		return jobDes;
	}
	
	private String constructPageUrl(String jobDes, String city, int page) {
		jobDes = jobDes.replace(" ", "+");
		return "https://www.simplyhired.ca/search?q=" + jobDes + "&l=" + city + "&pn=" + page;
	}

}
