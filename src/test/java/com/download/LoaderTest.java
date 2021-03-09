package com.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import com.downloader.configloader.Loader;
import com.downloader.configloader.UrlContainer;
import com.downloader.configloader.UrlData;

public class LoaderTest {
	/*
	 * Test whether 
	 * method is able to load YML url container or not
	 */
	@Test
	public void urlContainerLoadFromYml() {
		try {
			UrlContainer container = Loader.ymlLoader("LinkListTest.yml");
			assertEquals("D:/downloadedByJava/", container.getToDir());
			assertTrue(container.isSequenceGeneration());
			assertNotNull(container.getConfig());
			for (Entry<String, String> each : container.getConfig().entrySet()) {
				assertEquals("key1", each.getKey());
				assertEquals("value1", each.getValue());
			}
			assertNotNull(container.getList());
			for (UrlData each : container.getList()) {
				assertNotNull(each.getConfig());
				for (Entry<String, String> eachConfig : each.getConfig().entrySet()) {
					assertEquals("key2", eachConfig.getKey());
					assertEquals("value2", eachConfig.getValue());
				}
				assertEquals("https://cg-519a459a-0ea3-42c2-b7bc-fa1143481f74.s3-us-gov-west-1.amazonaws.com/bulk-downloads/2018/indiv18.zip", each.getPath());
				assertEquals("raghav", each.getPassword());
				assertEquals("sourabh", each.getUserName());
			}
		} catch (Exception e) {
			Assert.fail("Test failed : ");
		}
	}
	@Test
	public void negativeCaseTest() {
		try {
			UrlContainer container = Loader.ymlLoader("Abc.yml");
			assertNotNull(container.getConfig());
			Assert.fail("Test failed : ");
		} catch (Exception e) {
			
		}
	}

}
