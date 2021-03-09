package com.downloader.exception;

public class UrlFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message;

	public UrlFormatException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {

		return message;
	}

}
