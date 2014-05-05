package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.dto.FTPCredentials;

public interface FTPService {

	String copyLocalAndRemove(String ftpPath, String tempPath,
			FTPCredentials credentials);
	
	List<String> copyLocalsAndRemoveCampaigns (String ftpPath, String tempPath,
			FTPCredentials credentials);

	boolean upload(String filename, String destinationPath,
			FTPCredentials credentials);

}
