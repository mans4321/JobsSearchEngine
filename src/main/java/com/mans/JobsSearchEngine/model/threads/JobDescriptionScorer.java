package com.mans.JobsSearchEngine.model.threads;

import java.util.Collections;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

import com.mans.JobsSearchEngine.model.JobScorer;
import com.mans.JobsSearchEngine.model.job.JobDescription;
import com.mans.JobsSearchEngine.view.ListOfJobsView;
import com.mans.JobsSearchEngine.view.LoadingView;

public class JobDescriptionScorer {

	private int threadCount;

	private volatile int threadsCompleted;

	private WorkerThread[] workers; // the threads that compute the image

	private BlockingQueue<JobDescription> taskQueue;

	private TreeSet<JobDescription> sortedJob;

	private JobScorer jobScorer;

	private LoadingView loadingView;

	public JobDescriptionScorer(BlockingQueue<JobDescription> taskQueue, LoadingView loadingView, int threadCount) {
		this.taskQueue = taskQueue;
		this.threadCount = threadCount;
		this.loadingView = loadingView;
		sortedJob = new TreeSet<JobDescription>(Collections.reverseOrder());
	}

	public void score(JobScorer jobScorer) {
		this.jobScorer = jobScorer;
		initAndRunWorkerThread();
	}

	private void initAndRunWorkerThread() {
		workers = new WorkerThread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			workers[i] = new WorkerThread(i);
			workers[i].start();
		}
	}

	private class WorkerThread extends Thread {
		private int index;

		WorkerThread(int index) {
			this.index = index;
		}

		public void run() {
			try {
				while (true) {
					JobDescription task = taskQueue.take();
					if (task.getJobTitle() == null) {
						workCompleted();
						break;
					}
					new ScorerTask(task).run();
				}
			} catch (InterruptedException e) {
				workers[index] = new WorkerThread(index);
				workers[index].start();
			}
		}
	}

	synchronized private void workCompleted() {
		threadsCompleted++;
		
		if (threadsCompleted == 1) {
			informOtherThreads();
		} else if (threadsCompleted == threadCount) {
			initListOfJobsView();
			workers = null;
		}
	}

	private void initListOfJobsView() {
		loadingView.setVisible(false);
		JobDescription[] Joblist = new JobDescription[sortedJob.size()];
		sortedJob.toArray(Joblist);
		new ListOfJobsView(Joblist).setVisible(true);
	}

	private void informOtherThreads() {
		for (int i = 0; i < threadCount - 1; i++) // inform other workers
			taskQueue.add(new JobDescription());
	}

	private class ScorerTask implements Runnable {
		JobDescription jobDes;

		ScorerTask(JobDescription jobDes) {
			this.jobDes = jobDes;
		}

		@Override
		public void run() {
			double score = jobScorer.getScore(jobDes.getJobDes());
			jobDes.setScore(score);
			addJob(jobDes);
		}
	}

	synchronized private void addJob(JobDescription jobDes) {
		sortedJob.add(jobDes);
	}
}