
package com.downloader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.downloader.configloader.UrlData;
import com.downloader.constants.Constants;
import com.downloader.exception.RequestSyntaxException;
import com.downloader.protocols.Protocol;
import com.downloader.security.Authentication;

public class DownloadRequest {
	private static Logger logger = Logger.getLogger(DownloadRequest.class.getName());

	private HybridUrl hybridUrl;
	private File localTempFile;

	private long id;
	private int timeout ;
	private Protocol protocol;
	private Authentication authentication;
	private Map<String, String> headers = new HashMap<>();

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	/**
	 * @param UrlData : raw form or url detaild like uid and pwd
	 * @param toDir directory where you want to store your downloaded file
	 * @param id  unique id  for task which can be generated using counter and timestamp also 
	 * @return
	 * @throws MalformedURLException
	 * @throws RequestSyntaxException
	 */
	public DownloadRequest(UrlData urlData, String toDir, long id) throws MalformedURLException, RequestSyntaxException {
		if (urlData != null && toDir != null) {
			try {
				StringBuilder currentPath = new StringBuilder();
				currentPath.append(toDir).append(File.separator).append(id).append(".temp");
				this.hybridUrl = new HybridUrl(urlData.getPath(),urlData.getUserName(),urlData.getPassword());
				this.id = id;
				this.localTempFile = new File(currentPath.toString());
				
				if(urlData.getTimeout()!=0){
					this.timeout=urlData.getTimeout();
				}else {
					this.timeout=Constants.DEFAULT_READ_TIMEOUT;
				}
				loadProtocol();
				enableAuthentication(urlData);
			} catch (Exception e) {
				logger.error( id + "Error : " + e.toString());
				throw new RequestSyntaxException(id + "Error : " + e.toString());
			}
		} else {
			logger.error( "Error : Null UrlData: " + id);
			throw new RequestSyntaxException("Error : Null UrlData: " + id);
		}
	}

	/*
	 * add authentication to download request
	 * if mentioned in raw data 
	 * */
	
	private void enableAuthentication(UrlData urlData) {
		if (urlData.getUserName() != null && urlData.getPassword() != null) {
			authentication = new Authentication(urlData.getUserName(), urlData.getPassword());
		}
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
	/*
	 * load protocol from Factory 
	 * depending upon url protocol type  
	 * */
	
	private void loadProtocol() {
		if (hybridUrl != null) {
			protocol = ProtocolFactory.getProtocol(hybridUrl.getProtocol());

		}
	}

	public HybridUrl getHybridUrl() {
		return hybridUrl;
	}

	public void setHybridUrl(HybridUrl url) {
		this.hybridUrl = url;
	}

	public File getLocalTempFile() {
		return localTempFile;
	}

	public void setLocalTempFile(File targetFile) {
		this.localTempFile = targetFile;
	}



	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public long getId() {
		return id;
	}

}
