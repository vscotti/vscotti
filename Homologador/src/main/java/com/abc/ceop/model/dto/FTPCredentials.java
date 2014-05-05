package com.abc.ceop.model.dto;

public class FTPCredentials {

	private String ftpUrl;
	private String username;
	private String password;
	
	public FTPCredentials(String ftpUrl, String username, String password) {
		super();
		this.ftpUrl = ftpUrl;
		this.username = username;
		this.password = password;
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
