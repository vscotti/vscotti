package com.abc.ceop.exception;

public class FTPConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public FTPConnectionException() {
		super();
	}

	public FTPConnectionException(String message) {
		super(message);
	}

	public FTPConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPConnectionException(Throwable cause) {
		super(cause);
	}

}
