package com.mans.JobsSearchEngine.model.threads;

import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import org.jsoup.nodes.Document;

import com.mans.JobsSearchEngine.model.PageRequest;
import com.mans.JobsSearchEngine.model.apifetcher.FetchJobsAPI;
import com.mans.JobsSearchEngine.model.job.JobDescription;

public class JobDescriptionFetcher {

	private int threadCount;

	private volatile int threadsCompleted;

	private WorkerThread[] workers; // the threads that compute the image

	// holds individual job Des url
	private BlockingQueue<Entry<String, FetchJobsAPI>> taskQueue;

	private BlockingQueue<JobDescription> jobDesQueue;

	public JobDescriptionFetcher(BlockingQueue<Entry<String, FetchJobsAPI>> taskQueue,
			BlockingQueue<JobDescription> jobDesQueue, int threadCount) {

		this.taskQueue = taskQueue;
		this.jobDesQueue = jobDesQueue;
		this.threadCount = threadCount;
	}

	public void fetch() {
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
					Entry<String, FetchJobsAPI> task = taskQueue.take();
					if (task.getKey() == null) {
						workCompleted();
						break;
					}
					new DescriptionFetchingTask(task).run();
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
		}else if(threadsCompleted == threadCount){
			jobDesQueue.add(new JobDescription());//job done
			workers = null;
		}
	}

	private void informOtherThreads() {
		for (int i = 0; i < threadCount - 1; i++) // inform other workers
			taskQueue.add(new AbstractMap.SimpleEntry<>(null, null));
	}

	private class DescriptionFetchingTask implements Runnable {

		private Entry<String, FetchJobsAPI> task;
		private PageRequest pageRequest;

		DescriptionFetchingTask(Entry<String, FetchJobsAPI> task) {
			this.task = task;
			this.pageRequest = new PageRequest();
		}

		@Override
		public void run() {
			String url = task.getKey();
			Document page = pageRequest.getPsge(url);
			if (Objects.isNull(page))
				return;// couldn't Connect to page
			addJobDesToQueue(page, url);
		}

		private void addJobDesToQueue(Document page, String url) {
			FetchJobsAPI api = task.getValue();
			JobDescription jobDes = api.extractJobDescriptionOnPage(page);
			jobDes.setUrl(url);
			jobDesQueue.add(jobDes);
		}
	}
}
