package com.downloader;

import com.downloader.protocols.FTP;
import com.downloader.protocols.HTTP;
import com.downloader.protocols.HTTPS;
import com.downloader.protocols.Protocol;
import com.downloader.protocols.SFTP;

public class ProtocolFactory {
	
	private ProtocolFactory(){
		/*All member Static*/
	}
	public static Protocol getProtocol(String type) {
		if ("ftp".equals(type)) {
			return new FTP();
		} else if ("http".equals(type)) {
			return new HTTP();
		} else if ("https".equals(type)) {
			return new HTTPS();
		}else if ("sftp".equals(type)) {
			return new SFTP();
		}
		return null;
	}
}
