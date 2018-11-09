package com.mans.JobsSearchEngine.threads;

import java.util.Collections;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

import com.mans.JobsSearchEngine.model.JobDescription;
import com.mans.JobsSearchEngine.model.Score;
import com.mans.JobsSearchEngine.utility.WordList;
import com.mans.JobsSearchEngine.view.ListOfJobs;
import com.mans.JobsSearchEngine.view.Loading;

/**
 * A consumer that takes elements from a BlockingQueue
 * 
 * @author www.codejava.net
 */
public class Consumer implements Runnable {

	private BlockingQueue<JobDescription> queue;
	private Score score;
	private TreeSet<JobDescription> sortedJob;
	private Loading loading;

	public Consumer(BlockingQueue<JobDescription> queue, Loading loading) {
		this.queue = queue;
		this.score = new Score(WordList.getInstance().getJobSkils());
		sortedJob = new TreeSet<JobDescription>(Collections.reverseOrder());
		this.loading = loading;
	}



	public void run() {

		try {
			int j = 0;
			int i = 0;
			while (true) {

				JobDescription jobDescription = queue.take();
				j++;
				if (jobDescription.getJobTitle() == null 
						&jobDescription.getCompanyName() == null ) {
					j--;
					i++;
					if (i == 3) {
						showJobList();
						break;
					}
					continue;
				}

				consume(jobDescription);
			}

		} catch (InterruptedException ie) {

			ie.printStackTrace();
		}
	}

	private void showJobList() {
		if(loading != null)
			loading.setVisible(false);;
		JobDescription[] Joblist = new JobDescription[sortedJob.size()] ;
		sortedJob.toArray(Joblist);
		new ListOfJobs(Joblist).setVisible(true);
		
	}

	private void consume(JobDescription jobDescription) {
		jobDescription.setScore(score.getScore(jobDescription.getJobDes()));
		sortedJob.add(jobDescription);
	}
}