package com.downloader.protocols;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.downloader.DownloadRequest;
import com.downloader.exception.DownloadFail;
import com.jcraft.jsch.JSchException;

public interface Protocol {

	public void connect() throws JSchException, IOException, DownloadFail;

	/**
	 * @param download request 
	 * @return void
	 * @details initialise the connection 
	 */
	public void initConnection(DownloadRequest request) throws IOException, NoSuchAlgorithmException, KeyManagementException, JSchException;
	/**
	 * @param request object in which URL which contain formatted details 
	 * @return void
	 * @details Write the output into file 
	 */
	public void writeToFile(DownloadRequest request) throws IOException, DownloadFail, JSchException;
	/**
	 * @param 
	 * @return 
	 * @details disconnect the connection 
	 */
	public void disconnect();

}
