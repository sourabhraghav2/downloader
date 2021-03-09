package com.downloader.configloader;

import java.util.Map;

public class UrlData {
	private String path;
	private String userName;
	private String password;
	private int timeout ;
	Map<String, String> config;

	public UrlData() {
	}

	public UrlData(String path, String userName, String password) {
		super();
		this.path = path;
		this.userName = userName;
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

	public UrlData(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}