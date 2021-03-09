package com.downloader.exception;

public class RequestSyntaxException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message;

	public RequestSyntaxException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {

		return message;
	}

}
