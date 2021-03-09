package com.downloader.util;

import java.io.File;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.downloader.DownloadRequest;
import com.downloader.constants.Constants;

public class OtherUtil {
	private OtherUtil() {
		// All static member
	}

	private static Logger logger = Logger.getLogger(OtherUtil.class.getName());
	private static long sequenceId = 0L;

	public static String decideFileNameFromResponse(DownloadRequest dt, URLConnection conn) {
		String fname = null;
		if (conn != null && dt != null) {
			String cd = conn.getHeaderField(Constants.CONTENT_DISPOSITION);
			if (cd != null) {
				fname = cd.substring(cd.indexOf(Constants.CD_FNAME) + 1, cd.length() - 1);
			} else {
				if (dt.getHybridUrl() != null && dt.getHybridUrl().getPath() != null) {
					String url = dt.getHybridUrl().getPath();
					fname = url.substring(url.lastIndexOf('/') + 1);
				}
			}
			int length = 15;
			if (fname != null) {
				if (fname.length() > length) {
					return System.currentTimeMillis() + String.valueOf(fname.subSequence(fname.length() - length, fname.length())).replaceAll("[^A-Za-z0-9]", "") + ".default";
				} else if (fname.length() == 0) {
					fname = String.valueOf(System.currentTimeMillis()) + System.currentTimeMillis() + ".default";
				} else {
					fname = String.valueOf(System.currentTimeMillis()).concat(fname);
				}
			}
		}
		return fname;
	}

	public static void setHeaders(URLConnection uc, Map<String, String> headers) {
		if (headers != null && uc != null) {
			Iterator<String> itr = headers.keySet().iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				uc.addRequestProperty(key, headers.get(key));
			}
		}
	}

	public static boolean renameFile(String newFileName, File targetFile) {
		if (newFileName != null && !newFileName.equals("") && targetFile != null) {
			String path = targetFile.getAbsolutePath();
			String onlyPath = path.substring(0, path.lastIndexOf('\\') + 1);
			StringBuilder finalPath = new StringBuilder().append(onlyPath).append(newFileName);
			File newFile = new File(finalPath.toString());
			if (targetFile.exists()) {
				if(newFile.delete()){
					logger.info("File deleted : "+newFile.getPath());
				}
				if (targetFile.renameTo(newFile)) {
					logger.info("Renamed : " + finalPath);
					return true;
				} else {
					logger.error("Error coudn't rename");
					return false;
				}
			}
		}
		return false;
	}

	public static String decideFileName(DownloadRequest request) {
		if (request.getHybridUrl() != null) {
			String path = request.getHybridUrl().getPath();
			int index = path.lastIndexOf('/');
			return String.valueOf(System.currentTimeMillis()).concat(path.substring(index + 1, path.length()));
		}
		return null;
	}

	public static long generateThreadId(boolean sequenceGeneration) throws InterruptedException {

		if (sequenceGeneration) {
			OtherUtil.sequenceId++;
			return OtherUtil.sequenceId;
		} else {
			Thread.sleep(100);
			return System.currentTimeMillis();
		}

	}
}
