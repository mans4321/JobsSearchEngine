package com.mans.JobsSearchEngine.model;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mans.JobsSearchEngine.threads.Consumer;
import com.mans.JobsSearchEngine.threads.Producer;
import com.mans.JobsSearchEngineI.view.ListOfJobs;

public class mainC {

	public static void main(String[] args) {

//		List<String> jobTitleLsit = new ArrayList<String>();
//		jobTitleLsit.add("junior java developer");
//		List<String> cities =  new ArrayList<String>();
//		cities.add("toronto");
//		BlockingQueue<JobDescription> queue = new LinkedBlockingQueue<>();				
//		new Producer(cities,jobTitleLsit,queue).run();
//		Consumer consumer  = new Consumer(queue, null);
//		consumer.run();
		JobDescription [] d = new JobDescription[100];
		
		for(int i = 0; i < 100 ; i++)
			d[i] = new JobDescription("ee","fv","ff","ff","gg","fff");
		new ListOfJobs(d).setVisible(true);
		
		
		
	}

}
