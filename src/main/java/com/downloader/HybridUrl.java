package com.downloader;

import java.net.MalformedURLException;
import java.net.URL;

import com.downloader.exception.UrlFormatException;

public class HybridUrl {
	private URL legacyUrl;
	private String path;
	private String protocol;
	private String host;
	private String strUrl;

	public String getStrUrl() {
		return strUrl;
	}

	public void setStrUrl(String strUrl) {
		this.strUrl = strUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setLegacyUrl(URL url) {
		this.legacyUrl = url;
	}
	/**
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 * @throws MalformedURLException
	 * @throws UrlFormatException
	 */
	public HybridUrl(String url, String userName, String password) throws MalformedURLException, UrlFormatException {
		this.strUrl = url;
		this.host = extractHost(url);
		this.protocol = extractProtocol(url);
		this.path = extractPath(url);
		if (this.protocol != null) {
			if ("ftp".equals(protocol) && host.indexOf('@') < 0) {
				if (userName != null && !userName.isEmpty() && password != null && !password.isEmpty()) {
					StringBuilder newUrl = new StringBuilder();
					this.strUrl = newUrl.append(protocol).append("://").append(userName).append(":").append(password).append("@").append(host).append(path).toString();
				} else {
					throw new UrlFormatException("FTP :: userName or password missing");
				}
			}
			if (!"sftp".equals(protocol)) {
				this.legacyUrl = new URL(strUrl);
			}

		}
	}
	/**
	 * @param url
	 * @return path
	 */
	private String extractPath(String url) {
		int index = url.indexOf("://");
		if (index > 0) {
			String restUrl = url.substring(index + 3, url.length());
			if (!"".equals(restUrl)) {
				int startIndex = restUrl.indexOf('/');
				return restUrl.substring(startIndex, restUrl.length());
			}
		}
		return null;
	}
	/**
	 * @param url
	 * @return path
	 */
	private String extractProtocol(String url) {
		int index = url.indexOf("://");
		if (index > 0) {
			return url.substring(0, index);
		}
		return null;
	}
	/**
	 * @param url
	 * @return host
	 */
	private String extractHost(String url) {
		int index = url.indexOf("://");
		if (index > 0) {
			String restUrl = url.substring(index + 3, url.length());
			if (!"".equals(restUrl)) {

				return restUrl.split("/")[0];
			}
		}
		return null;
	}
	/**
	 * @return Legacy url 
	 */
	public URL getLegacyUrl() {
		return legacyUrl;
	}

}
