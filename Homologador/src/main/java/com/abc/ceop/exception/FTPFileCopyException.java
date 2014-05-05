package com.abc.ceop.exception;

public class FTPFileCopyException extends Exception {

	private static final long serialVersionUID = 1L;

	public FTPFileCopyException() {
		super();
	}

	public FTPFileCopyException(String message) {
		super(message);
	}

	public FTPFileCopyException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPFileCopyException(Throwable cause) {
		super(cause);
	}

}
