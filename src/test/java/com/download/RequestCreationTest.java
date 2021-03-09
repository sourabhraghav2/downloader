/**

 * 
 */

package com.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.downloader.DownloadRequest;
import com.downloader.configloader.UrlData;
import com.downloader.constants.Constants;

@RunWith(JUnit4.class)
public class RequestCreationTest {

	/*
	 * Test whether DownloadRequest is able to generate when UrlData is feeded
	 * to it
	 */
	@Test
	public void superTest() {
		String targetDir = "target";
		String url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		UrlData urlRequest = new UrlData(url);
		urlRequest.setUserName("testUserName");
		urlRequest.setPassword("testPassword");

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertEquals(1, request.getId());
			assertNotNull(request.getHybridUrl());
			assertNotNull(request.getLocalTempFile());
			assertEquals("target" + File.separator + "1.temp", request.getLocalTempFile().getPath());
			assertNotNull(request.getAuthentication());
			assertEquals(Constants.DEFAULT_READ_TIMEOUT, request.getTimeout());
			assertEquals("testUserName", request.getAuthentication().getUsername());
			assertEquals("testPassword", request.getAuthentication().getPassword());

		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}
	}

	@Test
	public void withCredentialsTest() {
		// with credentials
		String targetDir = "target";
		String url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		UrlData urlRequest = new UrlData(url);
		urlRequest.setUserName("testUserName");
		urlRequest.setPassword("testPassword");

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertNotNull(request.getAuthentication());
			assertEquals("testUserName", request.getAuthentication().getUsername());
			assertEquals("testPassword", request.getAuthentication().getPassword());

		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

		// without credentials

		targetDir = "target";
		url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		urlRequest = new UrlData(url);

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertNull(request.getAuthentication());
		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

	}

	@Test
	public void timeoutTest() {
		// with timeout
		String targetDir = "target";
		String url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		UrlData urlRequest = new UrlData(url);
		urlRequest.setTimeout(100);
		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertEquals(100, request.getTimeout());

		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

		// without timeout
		targetDir = "target";
		url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		urlRequest = new UrlData(url);
		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertEquals(Constants.DEFAULT_READ_TIMEOUT, request.getTimeout());

		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

	}

	@Test
	public void hybridUrl() {
		String targetDir = "target";
		String url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		UrlData urlRequest = new UrlData(url);

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertNotNull(request.getHybridUrl());
		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

	}

	@Test
	public void requestId() {
		String targetDir = "target";
		String url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		UrlData urlRequest = new UrlData(url);

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertEquals(1, request.getId());
		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

		// RequestId setting
		targetDir = "target";
		url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		urlRequest = new UrlData(url);

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, -1);
			assertEquals(-1, request.getId());
		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

	}

	@Test
	public void targetFileTest() {
		String targetDir = "target";
		String url = "ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db";
		UrlData urlRequest = new UrlData(url);

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, 1);
			assertNotNull(request.getLocalTempFile());
			assertEquals("target" + File.separator + "1.temp", request.getLocalTempFile().getPath());
		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}

		// negative id and target

		try {
			DownloadRequest request = new DownloadRequest(urlRequest, targetDir, -1);
			assertNotNull(request.getLocalTempFile());
			assertEquals("target" + File.separator + "-1.temp", request.getLocalTempFile().getPath());
		} catch (Exception e) {
			Assert.fail("Test failed : " + e.getMessage());
		}
	}

}
