package com.mans.JobsSearchEngine.controller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mans.JobsSearchEngine.model.JobScorer;
import com.mans.JobsSearchEngine.model.apifetcher.FetchJobsAPI;
import com.mans.JobsSearchEngine.model.apifetcher.IndeedAPI;
import com.mans.JobsSearchEngine.model.apifetcher.SimplyHiredAPI;
import com.mans.JobsSearchEngine.model.apifetcher.WorkopolisAPI;
import com.mans.JobsSearchEngine.model.job.JobDescription;
import com.mans.JobsSearchEngine.model.job.JobSearchInfo;
import com.mans.JobsSearchEngine.model.threads.JobDescriptionFetcher;
import com.mans.JobsSearchEngine.model.threads.JobDescriptionScorer;
import com.mans.JobsSearchEngine.model.threads.JobUrlFetcher;
import com.mans.JobsSearchEngine.view.LoadingView;

public class FetchJobFacade {

	private final int NUMBER_OF_RESULT_PAGES = 3;

	private final int URL_FETCHR_THREADS = 10;

	private final int DESCRIPTION_FETCHER_THREADS = 10;

	private final int SCORER_THREADS = 3;

	private List<FetchJobsAPI> jobsAPI;

	private BlockingQueue<Entry<String, FetchJobsAPI>> jobsUrlQueue;

	private BlockingQueue<JobDescription> jobsDesQueue;

	private JobDescriptionFetcher jobDesFetcher;

	private JobDescriptionScorer jobDesScorer;

	private JobUrlFetcher jobUrlFetcher;

	private LoadingView loadingView;

	public FetchJobFacade() {
		initAndLoadloadJobAPI();
		
		this.jobsUrlQueue = new LinkedBlockingQueue<>();
		this.jobsDesQueue = new LinkedBlockingQueue<>();
		this.loadingView = new LoadingView();
		
		jobUrlFetcher = new JobUrlFetcher(jobsUrlQueue, URL_FETCHR_THREADS);
		jobDesFetcher = new JobDescriptionFetcher(jobsUrlQueue, jobsDesQueue,
				DESCRIPTION_FETCHER_THREADS);
		
		jobDesScorer = new JobDescriptionScorer(jobsDesQueue, loadingView, 
				SCORER_THREADS);
		
	}

	private HashMap<String, FetchJobsAPI> initAndLoadloadJobAPI() {
		jobsAPI = new ArrayList<>();
		jobsAPI.add(new IndeedAPI());
		jobsAPI.add(new SimplyHiredAPI());
		jobsAPI.add(new WorkopolisAPI());
		return null;
	}

	public void fetch(JobSearchInfo searchInfo) {
		loadingView.setVisible(true);
		List<Entry<String, FetchJobsAPI>> resultPages = getResultPagesFromAllApi(searchInfo);
		jobUrlFetcher.fetch(resultPages);
		jobDesFetcher.fetch();
		JobScorer jobScorer = new JobScorer(searchInfo.getSkills());
		jobDesScorer.score(jobScorer);
	}

	private List<Entry<String, FetchJobsAPI>> getResultPagesFromAllApi(JobSearchInfo searchInfo) {
		List<Entry<String, FetchJobsAPI>> resultPages = new LinkedList<>();

		for (String title : searchInfo.getTitles())
			for (String city : searchInfo.getCities())
				for (FetchJobsAPI api : jobsAPI) {
					List<String> apiUrls = api.getResultPagesURL(title, city, NUMBER_OF_RESULT_PAGES);
					for (String url : apiUrls) {
						Entry<String, FetchJobsAPI> resultPageEntry = new AbstractMap.SimpleEntry<>(url, api);
						resultPages.add(resultPageEntry);
					}
				}
		return resultPages;
	}
}