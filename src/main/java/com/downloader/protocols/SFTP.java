package com.downloader.protocols;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.downloader.DownloadRequest;
import com.downloader.HybridUrl;
import com.downloader.constants.Constants;
import com.downloader.exception.DownloadFail;
import com.downloader.io.IOUtil;
import com.downloader.util.OtherUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTP implements Protocol {
	/**
	 * session which would take care of connection
	 */
	private Session session = null;
	private static Logger logger = Logger.getLogger(SFTP.class.getName());

	@Override
	public void connect() throws JSchException, IOException, DownloadFail {
		if (session != null) {
			session.connect();
		} else {
			throw new DownloadFail("Session Null");
		}
	}

	@Override
	public void initConnection(DownloadRequest request)
			throws IOException, NoSuchAlgorithmException, KeyManagementException, JSchException {
		if (request != null && request.getHybridUrl() != null) {
			HybridUrl url = request.getHybridUrl();
			JSch jsch = new JSch();
			if (url.getHost() != null && request.getAuthentication() != null) {
				session = jsch.getSession(request.getAuthentication().getUsername(), url.getHost());
				session.setPassword(request.getAuthentication().getPassword());
				session.setConfig("StrictHostKeyChecking", "no");
				session.setTimeout(Constants.DEFAULT_CONNECT_TIMEOUT);

			}
		}
	}

	public void writeToFile(DownloadRequest request) throws JSchException, IOException, DownloadFail {
		if (request != null && session != null && request.getHybridUrl() != null
				&& session.openChannel("sftp") != null) {

			InputStream is = null;
			File targetFile = null;
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");

			try {
				targetFile = request.getLocalTempFile();
				// Clean if any same name file already exist
				IOUtil.cleanUpFile(targetFile);

				// to download url
				String path = request.getHybridUrl().getPath();
				channel.connect();
				is = channel.get(path);

				// find the name of file to be stored
				String fname = OtherUtil.decideFileName(request);

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
				if (channel != null && channel.getSession() != null) {
					channel.getSession().disconnect();
				}
				if (is != null) {
					is.close();
				}
			}
		}

	}

	@Override
	public void disconnect() {
		if (session != null) {
			session.disconnect();
		} else {
			logger.error("Error in disconnecting");
		}
	}

}
