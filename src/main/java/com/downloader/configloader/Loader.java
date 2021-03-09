package com.downloader.configloader;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Loader {
	private Loader() {
		// Because Of static members
	}

	private static Logger logger = Logger.getLogger(Loader.class.getName());

	public static UrlContainer ymlLoader(String fileName) throws IOException {
		logger.info("Inside : ymlLoader");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		ObjectMapper om = new ObjectMapper(new YAMLFactory());
		logger.info("Exiting : ymlLoader");
		return om.readValue(file, UrlContainer.class);
	}
}
