package com.abc.ceop.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;

public class FTPUtils {

	private FTPUtils() {
	}

	public static void setUpOutputFTPServer(String ftpPath, String ftpUsername, String ftpPassword,
			FakeFtpServer fakeFtpServer) {
		fakeFtpServer.setServerControlPort(6022);
		UserAccount userAccount = new UserAccount(ftpUsername, ftpPassword, "C:/");
		fakeFtpServer.addUserAccount(userAccount);
		FileSystem fileSystem = new WindowsFakeFileSystem();
		fileSystem.add(new DirectoryEntry("C:/" + ftpPath));
		fakeFtpServer.setFileSystem(fileSystem);
		fakeFtpServer.start();
	}
	
	public static void setUpInputFTPServer(String ftpPath, String ftpUsername, String ftpPassword, FakeFtpServer fakeFtpServer,
			String... fileNames) throws IOException {
		fakeFtpServer.setServerControlPort(6021);
		UserAccount userAccount = new UserAccount(ftpUsername,
				ftpPassword, "C:/");
		fakeFtpServer.addUserAccount(userAccount);
		FileSystem fileSystem = new WindowsFakeFileSystem();
		int i = 0;
		for (String fileName : fileNames) {
			fileSystem.add(new FileEntry("C:/" + ftpPath + "/" + fileName + i++,
					FileUtils.readFileToString(new File("src/test/resources/" + fileName))));
		}
		fakeFtpServer.setFileSystem(fileSystem);
		fakeFtpServer.start();
	}
	
}
