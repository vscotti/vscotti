package com.abc.ceop.exception;

public class FTPLoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public FTPLoginException() {
		super();
	}

	public FTPLoginException(String message) {
		super(message);
	}

	public FTPLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPLoginException(Throwable cause) {
		super(cause);
	}

}