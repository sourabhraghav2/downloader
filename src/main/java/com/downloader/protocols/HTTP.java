package com.downloader.protocols;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.apache.log4j.Logger;

import com.downloader.DownloadRequest;
import com.downloader.HybridUrl;
import com.downloader.constants.Constants;
import com.downloader.exception.DownloadFail;
import com.downloader.io.IOUtil;
import com.downloader.util.OtherUtil;
import com.jcraft.jsch.JSchException;

public class HTTP implements Protocol {
	HttpURLConnection conn = null;
	private static Logger logger = Logger.getLogger(HTTP.class.getName());

	public void initConnection(DownloadRequest request) throws IOException {

		if (request != null && request.getHybridUrl() != null && request.getHybridUrl().getLegacyUrl() != null) {
			HybridUrl url = request.getHybridUrl();
			conn = (HttpURLConnection) url.getLegacyUrl().openConnection();
			conn.setRequestMethod(Constants.HTTP_REQUEST_METHOD);
			OtherUtil.setHeaders(conn, request.getHeaders());
			conn.setReadTimeout(request.getTimeout());
			conn.setDoOutput(true);
			conn.setConnectTimeout(Constants.DEFAULT_CONNECT_TIMEOUT);
		}
	}

	@Override
	public void connect() throws JSchException, IOException, DownloadFail {
		if (conn != null) {
			conn.connect();
		} else {
			throw new DownloadFail("Connection Null");
		}
	}

	public void writeToFile(DownloadRequest request) throws IOException, DownloadFail {
		if (request != null && conn != null) {
			String fname = OtherUtil.decideFileNameFromResponse(request, conn);

			InputStream is = null;
			File targetFile = null;
			try {
				targetFile = request.getLocalTempFile();
				// Clean if any same name file already exist
				IOUtil.cleanUpFile(targetFile);

				is = conn.getInputStream();

				// start writing
				logger.info("Started writing into :  " + targetFile.getAbsolutePath() + " : " + fname);
				IOUtil.writingFileWithAccidentalCleanUp(targetFile, is);
				logger.info("Writing finished : " + fname);

				// renaming
				OtherUtil.renameFile(fname, targetFile);
			} catch (Exception e) {
				// inform parent about the error
				logger.error("Error in download : " + e.toString() + " : " + request.getId());
				throw new DownloadFail("Error in downloading : " + request.getId());
			} finally {
				logger.info("Exiting thread: " + request.getId());
				if (is != null) {
					is.close();
				}
			}
		} else {
			logger.error("Error Null connection or request");
			throw new DownloadFail("Error Null connection or request");
		}
	}

	@Override
	public void disconnect() {
		if (conn != null) {
			conn.disconnect();
		} else {
			logger.error("Error in disconnecting");
		}

	}

}
