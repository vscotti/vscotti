package com.abc.ceop.exception;

public class FTPFileRemovalException extends Exception {

	private static final long serialVersionUID = 1L;

	public FTPFileRemovalException() {
		super();
	}

	public FTPFileRemovalException(String message) {
		super(message);
	}

	public FTPFileRemovalException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPFileRemovalException(Throwable cause) {
		super(cause);
	}
	
}