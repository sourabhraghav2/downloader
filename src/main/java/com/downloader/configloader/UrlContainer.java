package com.downloader.configloader;

import java.util.List;
import java.util.Map;

public class UrlContainer {
	private List<UrlData> list;
	private String toDir;
	Map<String, String> config;
	private boolean sequenceGeneration;

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

	public boolean isSequenceGeneration() {
		return sequenceGeneration;
	}

	public void setSequenceGeneration(boolean sequenceGeneration) {
		this.sequenceGeneration = sequenceGeneration;
	}

	public String getToDir() {
		return toDir;
	}

	public void setToDir(String toDir) {
		this.toDir = toDir;
	}

	public List<UrlData> getList() {
		return list;
	}

	public void setList(List<UrlData> list) {
		this.list = list;
	}

}
