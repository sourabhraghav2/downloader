/**

 * 
 */

package com.download;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.downloader.DownloadingManager;
import com.downloader.configloader.UrlContainer;
import com.downloader.configloader.UrlData;

@RunWith(JUnit4.class)
public class ProtocolTest {
	private static final String FAIL_MSG = "Test failed : ";
	private String basePathForResuorces = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator;
	private static Logger logger = Logger.getLogger(ProtocolTest.class.getName());

	/*
	 * Test whether method is able to download ftp downoading request
	 */
	@Test
	public void ftpTest() {
		try {
			String existingFileName = "ftp.tst";
			File existingFile = new File(basePathForResuorces + existingFileName);
			String targetDir = "target" + File.separator + "ftp";

			// Create and delete and create again the directory
			Files.createDirectories(Paths.get(targetDir));
			FileUtils.deleteDirectory(new File(targetDir));
			Files.createDirectories(Paths.get(targetDir));

			// Url Data
			List<UrlData> list = new ArrayList<>();
			UrlData urlData = new UrlData("ftp://speedtest:speedtest@ftp.otenet.gr/test100k.db");
			urlData.setTimeout(30000);
			list.add(urlData);
			UrlContainer container = new UrlContainer();
			container.setList(list);
			container.setToDir(targetDir);
			container.setSequenceGeneration(true);

			// Data Loaded to downloader
			DownloadingManager dm = new DownloadingManager(container);
			dm.startDownloading();

			// wait till it gets downloaded hopefully within 10 sec
			Thread.sleep(10000);
			File file = null;

			// Downloaded file
			for (final File fileEntry : (new File(targetDir)).listFiles()) {
				file = fileEntry;
			}
			if (file != null) {
				String actualMd5 = DigestUtils.md5Hex(new FileInputStream(file));
				String expectedMd5 = DigestUtils.md5Hex(new FileInputStream(existingFile));
				// Check downloaded and existing file equality
				assertEquals(expectedMd5, actualMd5);
			} else {
				logger.error("No file downloaded ");
				Assert.fail(FAIL_MSG);
			}

		} catch (Exception e) {
			logger.error("Error : " + e);
			Assert.fail(FAIL_MSG + e.getMessage());

		}
	}

	/*
	 * Test whether method is able to download http downoading request
	 */
	@Test
	public void httpTest() {
		try {
			String existingFileName = "http.tst";
			File existingFile = new File(basePathForResuorces + existingFileName);
			String targetDir = "target" + File.separator + "http";

			// Create and delete and create again the directory
			Files.createDirectories(Paths.get(targetDir));
			FileUtils.deleteDirectory(new File(targetDir));
			Files.createDirectories(Paths.get(targetDir));

			List<UrlData> list = new ArrayList<>();
			list.add(new UrlData("http://speedtest.ftp.otenet.gr/files/test100k.db"));
			UrlContainer container = new UrlContainer();
			container.setList(list);
			container.setToDir(targetDir);
			container.setSequenceGeneration(true);

			// Data Loaded to downloader
			DownloadingManager dm = new DownloadingManager(container);
			dm.startDownloading();

			// wait till it gets downloaded hopefully within 10 sec
			Thread.sleep(10000);
			File file = null;

			// Downloaded file
			for (final File fileEntry : (new File(targetDir)).listFiles()) {
				file = fileEntry;
			}
			String actualMd5 = DigestUtils.md5Hex(new FileInputStream(file));
			String expectedMd5 = DigestUtils.md5Hex(new FileInputStream(existingFile));

			// Check downloaded and existing file equality
			assertEquals(expectedMd5, actualMd5);

		} catch (Exception e) {
			logger.error("Error : " + e);
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException, InterruptedException {
		ProtocolTest pt = new ProtocolTest();
		pt.sftpTest();
	}

	/*
	 * Test whether method is able to download https downoading request
	 */
	@Test
	public void httpsTest() {
		try {
			String existingFileName = "https.tst";
			File existingFile = new File(basePathForResuorces + existingFileName);
			String targetDir = "target" + File.separator + "https";

			// Create and delete and create again the directory
			Files.createDirectories(Paths.get(targetDir));
			FileUtils.deleteDirectory(new File(targetDir));
			Files.createDirectories(Paths.get(targetDir));

			List<UrlData> list = new ArrayList<>();
			list.add(new UrlData("https://apis.google.com/js/api.js"));
			UrlContainer container = new UrlContainer();
			container.setList(list);
			container.setToDir(targetDir);
			container.setSequenceGeneration(true);

			// Data Loaded to downloader
			DownloadingManager dm = new DownloadingManager(container);
			dm.startDownloading();

			// wait till it gets downloaded hopefully within 10 sec
			Thread.sleep(10000);
			File file = null;

			// Downloaded file
			for (final File fileEntry : (new File(targetDir)).listFiles()) {
				file = fileEntry;
			}
			String actualMd5 = DigestUtils.md5Hex(new FileInputStream(file));
			String expectedMd5 = DigestUtils.md5Hex(new FileInputStream(existingFile));

			// Check downloaded and existing file equality
			assertEquals(expectedMd5, actualMd5);

		} catch (Exception e) {
			logger.error("Error : " + e);
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}

	/*
	 * Test whether method is able to download sftp downoading request
	 */
	@Test
	public void sftpTest() throws MalformedURLException, FileNotFoundException, InterruptedException {
		try {
			String existingFileName = "sftp.tst";
			File existingFile = new File(basePathForResuorces + existingFileName);
			String targetDir = "target" + File.separator + "sftp";

			// Create and delete and create again the directory
			Files.createDirectories(Paths.get(targetDir));
			FileUtils.deleteDirectory(new File(targetDir));
			Files.createDirectories(Paths.get(targetDir));

			List<UrlData> list = new ArrayList<>();
			UrlData urlData = new UrlData("sftp://165.114.28.223/appdata/picusr/Pic/pmt/stdlib/2D/2DBusinessRules.tcl");
			urlData.setPassword("Lkjh^789");
			urlData.setUserName("picusr");
			list.add(urlData);
			UrlContainer container = new UrlContainer();
			container.setList(list);
			container.setToDir(targetDir);
			container.setSequenceGeneration(true);

			// Data Loaded to downloader
			DownloadingManager dm = new DownloadingManager(container);
			dm.startDownloading();
			// wait till it gets downloaded hopefully within 10 sec
			Thread.sleep(10000);
			File file = null;

			// Downloaded file
			for (final File fileEntry : (new File(targetDir)).listFiles()) {
				file = fileEntry;
			}
			if (file != null) {
				String actualMd5 = DigestUtils.md5Hex(new FileInputStream(file));
				String expectedMd5 = DigestUtils.md5Hex(new FileInputStream(existingFile));

				// Check downloaded and existing file equality
				assertEquals(expectedMd5, actualMd5);
			} else {
				logger.error("Error : ");
				Assert.fail(FAIL_MSG);
			}
		} catch (Exception e) {
			logger.error("Error : " + e);
			Assert.fail(FAIL_MSG + e.getMessage());
		}

	}

}
