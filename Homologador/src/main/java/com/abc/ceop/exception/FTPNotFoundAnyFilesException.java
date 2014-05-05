package com.abc.ceop.exception;

public class FTPNotFoundAnyFilesException extends Exception {

	private static final long serialVersionUID = 1L;

	public FTPNotFoundAnyFilesException() {
		super();
	}

	public FTPNotFoundAnyFilesException(String message) {
		super(message);
	}

	public FTPNotFoundAnyFilesException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPNotFoundAnyFilesException(Throwable cause) {
		super(cause);
	}

}