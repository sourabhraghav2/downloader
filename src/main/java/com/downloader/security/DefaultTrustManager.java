package com.downloader.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public final class DefaultTrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		//Trust all the clients
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		//Trust all the clients and no exception thrown
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
