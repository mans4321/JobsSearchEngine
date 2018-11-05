package com.mans.JobsSearchEngine.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.mans.JobsSearchEngine.model.JobDescription;
import com.mans.JobsSearchEngine.search.Indeed;
import com.mans.JobsSearchEngine.search.SearchWebsites;
import com.mans.JobsSearchEngine.search.SimplyHired;
import com.mans.JobsSearchEngine.search.Workopolis;

public class Producer {

	private List<String> cities;
	private List<String> jobDes;
	private BlockingQueue<JobDescription> queue;
	
	public Producer(List<String> cities, List<String> jobDes, 
			BlockingQueue<JobDescription> queue) {
		this.cities = cities;
		this.jobDes = jobDes;
		this.queue = queue;
	}

	
	public void run(){
		List<SearchWebsites> websites = new ArrayList<>(); 
		websites.add(new Indeed(cities, jobDes, queue));
		websites.add( new SimplyHired(cities, jobDes, queue));
		websites.add(new Workopolis(cities, jobDes, queue));
		
		for(SearchWebsites website : websites)
			new Thread(() ->website.search()).start();

	}
}
