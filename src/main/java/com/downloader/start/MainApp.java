package com.downloader.start;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.downloader.DownloadingManager;
import com.downloader.configloader.Loader;
import com.downloader.configloader.UrlContainer;
import com.downloader.exception.DownloadFail;

public class MainApp {

	private static Logger logger = Logger.getLogger(MainApp.class.getName());

	public static void main(String[] args) throws DownloadFail {
		UrlContainer container;
		try {
			container = Loader.ymlLoader("LinkList.yml");
			if (container != null && container.getList() != null && !container.getList().isEmpty()) {
				DownloadingManager manager = new DownloadingManager(container);
				manager.startDownloading();
			} else {
				throw new DownloadFail("Url List is not correct");
			}
		} catch (IOException e) {
			logger.error( "ERROR : Cannot start : " + e);
		}
	}
}
