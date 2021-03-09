package com.downloader.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.downloader.constants.Constants;
import com.downloader.exception.DownloadFail;

public class IOUtil {
	private static Logger logger = Logger.getLogger(IOUtil.class.getName());

	private IOUtil() {
		// All static members
	}

	/*
	 * Delete the file whenever required
	 * 
	 */
	public static boolean cleanUpFile(File file) {
		boolean response = false;
		if (file != null && file.delete()) {
			response = true;
		}
		return response;

	}

	public static void writingFileWithAccidentalCleanUp(File targetFile, InputStream is) throws DownloadFail {
		try (OutputStream os = new FileOutputStream(targetFile)) {
			byte[] buff = new byte[Constants.BUFFER_SIZE];
			int bytesRead = 0;
			while ((bytesRead = is.read(buff)) != -1) {
				os.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.info("Removinng file " + targetFile);
			boolean isFileDeleted = IOUtil.cleanUpFile(targetFile);
			logger.info("File deletion : " + isFileDeleted);
			throw new DownloadFail(e.toString());
		}
	}

	/*
	 * Delete the file whenever required
	 * 
	 */
	public static void writeToFile(OutputStream os, InputStream is) throws IOException {
		byte[] buff = new byte[Constants.BUFFER_SIZE];
		int bytesRead = 0;
		while ((bytesRead = is.read(buff)) != -1) {
			os.write(buff, 0, bytesRead);
		}
		
	}

	
}
