package com.abc.ceop.exception;

public class FTPRetrieveFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public FTPRetrieveFileException() {
		super();
	}

	public FTPRetrieveFileException(String message) {
		super(message);
	}

	public FTPRetrieveFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPRetrieveFileException(Throwable cause) {
		super(cause);
	}

}