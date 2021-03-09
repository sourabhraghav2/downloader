package com.download;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.downloader.ProtocolFactory;
import com.downloader.protocols.FTP;
import com.downloader.protocols.HTTP;
import com.downloader.protocols.HTTPS;
import com.downloader.protocols.Protocol;
import com.downloader.protocols.SFTP;

public class ProtocolFactoryTest {
	/*
	 * Test whether 
	 * Factory is able to load required/correct protocol 
	 */
	@Test
	public void ftpTest() {
		Protocol protocol = ProtocolFactory.getProtocol("ftp");
		assertTrue(protocol instanceof FTP);
	}

	@Test
	public void sftpTest() {
		Protocol protocol = ProtocolFactory.getProtocol("sftp");
		assertTrue(protocol instanceof SFTP);
	}
	
	@Test
	public void httpTest() {
		Protocol protocol = ProtocolFactory.getProtocol("http");
		assertTrue(protocol instanceof HTTP);
	}

	@Test
	public void httpsTest() {
		Protocol protocol = ProtocolFactory.getProtocol("https");
		assertTrue(protocol instanceof HTTPS);
	}

}
