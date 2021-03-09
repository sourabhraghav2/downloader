package com.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.gen5.api.Assertions;

import com.downloader.DownloadRequest;
import com.downloader.configloader.UrlData;
import com.downloader.io.IOUtil;
import com.downloader.util.OtherUtil;

public class UtilTest {
	private static final String FAIL_MSG = "Test failed : ";
	private String basePathForResuorces = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator;
	private static Logger logger = Logger.getLogger(UtilTest.class.getName());

	/*
	 * Test whether function is able to rename temp file to required fileName or
	 * not
	 */
	@Test
	public void renameFileTest() {
		try {
			String targetFile = "target" + File.separator + System.currentTimeMillis() + ".txt";
			FileOutputStream fo = new FileOutputStream(targetFile);
			fo.close();
			assertEquals(true, OtherUtil.renameFile("Sourabh.txt", new File(targetFile)));
			assertEquals(true, new File("target/Sourabh.txt").exists());

		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}

	/*
	 * Test whether generated file name is not empty
	 */
	@Test
	public void decideFileNameTest() {
		try {
			UrlData urlData = new UrlData("sftp://165.114.28.223/appdata/picusr/Pic/pmt/stdlib/2D/2DBusinessRules.tcl");
			String fileName = OtherUtil.decideFileName(new DownloadRequest(urlData, "target", 1));
			logger.info("FileName : " + fileName);
			if (fileName != null) {
				Assertions.assertNotEquals("", fileName);
			} else {
				Assert.fail(FAIL_MSG);
			}

		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}

	/*
	 * Test whether generated thread id are unique or not
	 */
	@Test
	public void generateThreadIdTest() {
		try {
			logger.info("generateThreadIdTest start");
			Set<Long> idLit = new HashSet<>();
			for (int i = 0; i < 100; ++i) {
				long id = OtherUtil.generateThreadId(true);
				Assertions.assertTrue(idLit.add(id));
			}
			for (int i = 0; i < 10; ++i) {
				long id = OtherUtil.generateThreadId(false);
				Assertions.assertTrue(idLit.add(id));
			}
			logger.info("generateThreadIdTest end");

		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}

	
	/*
	 * Test whether IOUtil.writeFileAndUpdatListener function is writing correct
	 * data to file
	 */
	@Test
	public void writeFileTest() {
		try {
			File targetFile = new File(basePathForResuorces + "copiedFile.tst");
			Files.delete(targetFile.toPath());

			File inputFile = new File(basePathForResuorces + "toCopyFile.tst");
			InputStream is = new FileInputStream(inputFile);
			IOUtil.writingFileWithAccidentalCleanUp(targetFile, is);
			String actualMd5 = DigestUtils.md5Hex(new FileInputStream(targetFile));
			String expectedMd5 = DigestUtils.md5Hex(new FileInputStream(inputFile));
			assertEquals(expectedMd5, actualMd5);
			logger.info("writeFileTest end");
		} catch (Exception e) {
			Assert.fail(FAIL_MSG + e.getMessage());
		}
	}

	/*
	 * Test whether IOUtil.writeFileAndUpdatListener function is writing correct
	 * data to file
	 */
	@Test
	public void accidentalFileCleanUpTest() {
		File targetFile = new File(basePathForResuorces + "accidentalFileCleanUp.tst");
		try {
			if(targetFile.createNewFile()){
				logger.info("File created : "+targetFile.getPath());
			}
			InputStream downloadFrom = new InputStream() {
				@Override
				public int read() throws IOException {
					throw new IOException("Dummy Exception");
				}
			};
			IOUtil.writingFileWithAccidentalCleanUp(targetFile, downloadFrom);
		} catch (Exception e) {
			assertFalse(targetFile.exists());
		}
	}
	
/*	
	 * Test whether IOUtil.writeFileAndUpdatListener function is writing correct
	 * data to file
	 
	@Test
	public void slowConnectionResourcesTest() {
		File targetFile = new File(basePathForResuorces + "slowSpeedDownload.tst");
		try {
			
			targetFile.createNewFile();
			InputStream downloadFrom = new InputStream() {
			
				
				@Override
				public int read() throws IOException {
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return -1;
				}
				
			};
			

			
			long yourmilliseconds = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");    
			Date resultdate = new Date(yourmilliseconds);
			
			logger.info("Time : "+sdf.format(resultdate));
			IOUtil.writingFileWithAccidentalCleanUp(targetFile, downloadFrom);
			
			yourmilliseconds = System.currentTimeMillis();
			sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");    
			resultdate = new Date(yourmilliseconds);
			logger.info("Time : "+sdf.format(resultdate));
		} catch (Exception e) {
			
		}
	}
*/

}
