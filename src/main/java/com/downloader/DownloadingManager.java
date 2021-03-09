
package com.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.downloader.configloader.UrlContainer;
import com.downloader.configloader.UrlData;
import com.downloader.constants.Constants;
import com.downloader.protocols.Protocol;
import com.downloader.util.OtherUtil;

public class DownloadingManager {

	private static Logger logger = Logger.getLogger(DownloadingManager.class.getName());
	private ExecutorService executorService = new ThreadPoolExecutor(Constants.NUMBER_OF_CORE, Constants.MAX_POOL_SIZE, Constants.TIMEOUT, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(Constants.MAX_POOL_SIZE));
	private final ArrayList<DownloadRequest> tasks = new ArrayList<>();
	private List<UrlData> urlList = null;
	private String toDir;
	private boolean sequenceGeneration;

	public DownloadingManager(UrlContainer listObj) {
		this.urlList = listObj.getList();
		this.toDir = listObj.getToDir();
		this.sequenceGeneration = listObj.isSequenceGeneration();
		prepareTaskList();
	}
	
	/*
	 * Create Download Request list from raw url Data 
	 * 
	 */
	public void prepareTaskList() {
		for (UrlData each : urlList) {
			try {
				tasks.add(new DownloadRequest(each, toDir, OtherUtil.generateThreadId(sequenceGeneration)));
			} catch (Exception e) {
				logger.error("Cannot start download thread for : " + each.getPath());
			}
		}
	}
	
	/*
	 *  
	 * this method is responsible for givig Thread a task to do
	 * and if thread trigger error then clean up the file which is half downloaded
	 * and at the end shut down the executor
	 */
	public void startDownloading() {

		if (executorService != null) {
			logger.info("Starting download");
			for (DownloadRequest each : tasks) {
				executorService.execute(() -> {
					logger.info("Task assigned to Thread:" + each.getId());
					Protocol protocol=null;
					try {
						protocol= each.getProtocol();
						protocol.initConnection(each);
						protocol.connect();
						protocol.writeToFile(each);
						protocol.disconnect();
					} catch (Exception e) {
						logger.error("Error in download thread : " + e.toString());
						logger.error("Errored Thread Details : " + each.getId() + " : " + each.getHybridUrl().getStrUrl());
					} finally {
						if(protocol!=null){
							protocol.disconnect();
						}
						logger.info("Exiting thread : " + each.getId() + " : " + each.getHybridUrl().getStrUrl());
					}
				});
			}
			logger.info("Downloader started, waiting for tasks.");
			executorService.shutdown();
		} else {
			logger.error("Couldn't start");
		}

	}
	
	
}
