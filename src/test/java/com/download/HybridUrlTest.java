package com.download;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.downloader.HybridUrl;

public class HybridUrlTest {
	private static Logger logger = Logger.getLogger(HybridUrlTest.class.getName());
	private static final String FAIL_MSG = "Test failed : ";

	/*
	 * Test whether method is able to embed the username and password to url or
	 * not
	 */
	@Test
	public void ftpHostWithNegativeUserNamePassword() {
		HybridUrl url = null;
		try {
			// with username & password
			url = new HybridUrl("ftp://ftp.otenet.gr/test100k.db", "speedtest", "speed@test");
			logger.info("url : " + url.getLegacyUrl());
			assertEquals("ftp://speedtest:speed@test@ftp.otenet.gr/test100k.db", url.getLegacyUrl().toString());
			
		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}
	
	@Test
	public void ftpHostWithUserNamePassword() {
		HybridUrl url = null;
		try {
			// with username & password
			url = new HybridUrl("ftp://ftp.otenet.gr/test100k.db", "speedtest", "speedtest");
			logger.info("url : " + url.getLegacyUrl());
			assertEquals("ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db", url.getLegacyUrl().toString());
			// without username & password
			url = new HybridUrl("ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db", null, null);
			logger.info("url : " + url.getLegacyUrl());
			assertEquals("ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db", url.getLegacyUrl().toString());
		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}
	/*
	 * Test whether method 
	 * is able to construct the hostname or not
	 * 
	 */
	@Test
	public void testHost() {
		HybridUrl url = null;
		try {
			url = new HybridUrl("http://speedtest.ftp.otenet.gr/files/test100Mb.db", null, null);
			logger.info("host : " + url.getHost());
			assertEquals("speedtest.ftp.otenet.gr", url.getHost());
		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}
	/*
	 * Test whether method 
	 * is able to construct the protocol or not
	 * 
	 */
	@Test
	public void testProtocol() {
		HybridUrl url = null;
		try {
			url = new HybridUrl("http://speedtest.ftp.otenet.gr/files/test100Mb.db", null, null);
			logger.info("protocol : " + url.getProtocol());
			assertEquals("http", url.getProtocol());
		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}
	/*
	 * Test whether method 
	 * is able to construct the path or not
	 * 
	 */
	@Test
	public void testPath() {
		HybridUrl url = null;
		try {
			url = new HybridUrl("http://speedtest.ftp.otenet.gr/files/test100Mb.db", null, null);
			logger.info("path : " + url.getPath());
			assertEquals("/files/test100Mb.db", url.getPath());
		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}

	}

}
