package com.mans.JobsSearchEngine.model.threads;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.nodes.Document;

import com.mans.JobsSearchEngine.model.PageRequest;
import com.mans.JobsSearchEngine.model.apifetcher.FetchJobsAPI;

public class JobUrlFetcher {

	private int threadCount;

	private WorkerThread[] workers; // the threads that compute the image

	private volatile int threadsCompleted; // how many threads have finished?

	private ConcurrentLinkedQueue<Runnable> taskQueue; // holds tasks

	// holds individual job Des url
	private BlockingQueue<Entry<String, FetchJobsAPI>> jobUrlQueue;

	public JobUrlFetcher(BlockingQueue<Entry<String, FetchJobsAPI>> jobUrlQueue, int threadCount) {
		this.threadCount = threadCount;
		this.jobUrlQueue = jobUrlQueue;
	}

	public void fetch(List<Entry<String, FetchJobsAPI>> resultPage) {
		initTaskQueue(resultPage);
		initAndRunWorkerThread();
	}

	private void initTaskQueue(List<Entry<String, FetchJobsAPI>> resultPage) {
		taskQueue = new ConcurrentLinkedQueue<Runnable>();
		for (Entry<String, FetchJobsAPI> task : resultPage) {
			taskQueue.add(new UrlFetchingTask(task));
		}
	}

	private void initAndRunWorkerThread() {
		workers = new WorkerThread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			workers[i] = new WorkerThread();
			workers[i].start();
		}
	}

	private class WorkerThread extends Thread {
		public void run() {
			while (true) {
				Runnable task = taskQueue.poll();
				if (task == null)
					break;
				task.run();
			}
			threadFinished();
		}
	}

	synchronized private void threadFinished() {
		threadsCompleted++;
		if (threadsCompleted == workers.length) { // all threads have finished
			jobUrlQueue.add(new AbstractMap.SimpleEntry<>(null, null)); // used
																		// to
																		// signal
																		// that
																		// no
																		// more
																		// tasks
																		// will
			// be added
			workers = null;
		}
	}

	private class UrlFetchingTask implements Runnable {

		private Entry<String, FetchJobsAPI> task;
		private PageRequest pageRequest;

		UrlFetchingTask(Entry<String, FetchJobsAPI> task) {
			this.task = task;
			this.pageRequest = new PageRequest();
		}

		@Override
		public void run() {
			String url = task.getKey();
			Document page = pageRequest.getPsge(url);
			if (Objects.isNull(page))
				return;// couldn't Connect to page
			addJobDesUrlToQueue(page);
		}

		private void addJobDesUrlToQueue(Document page) {
			FetchJobsAPI api = task.getValue();
			for (String jobDesUrl : api.getJobsUrlsOnPage(page)) {
				Entry<String, FetchJobsAPI> jobDescription = new AbstractMap.SimpleEntry<>(jobDesUrl, api);
				jobUrlQueue.add(jobDescription);
			}
		}
	}
}
