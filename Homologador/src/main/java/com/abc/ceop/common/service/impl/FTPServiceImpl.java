package com.abc.ceop.common.service.impl;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.FTPService;
import com.abc.ceop.exception.FTPConnectionException;
import com.abc.ceop.exception.FTPFileRemovalException;
import com.abc.ceop.exception.FTPLoginException;
import com.abc.ceop.exception.FTPNotFoundAnyFilesException;
import com.abc.ceop.exception.FTPRetrieveFileException;
import com.abc.ceop.model.dto.FTPCredentials;
import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.phoneapprover.service.FirstProcessConfigurationService;

@Service
public class FTPServiceImpl implements FTPService {

	private final Logger logger = LoggerFactory.getLogger(FTPServiceImpl.class);

	@Resource
	private  FirstProcessConfigurationService firstProcessConfigurationService; 
	
	@Override
	public String copyLocalAndRemove(String ftpPath, String tempPath, FTPCredentials credentials) {
		FTPClient client = null;
		String fileFoundAtFtpPath = null;
		try {
			client = connect(credentials);
			fileFoundAtFtpPath = getFirstFileFromFTP(client, ftpPath);
			copyLocal(client, tempPath, ftpPath, fileFoundAtFtpPath);
			deleteFileFromFtp(client, ftpPath, fileFoundAtFtpPath);
			return tempPath + "/" + new File(fileFoundAtFtpPath).getName();
		} catch (FTPConnectionException ftpConnectionException) {
			logger.error("No se puede conectar al FTP {}", credentials.getFtpUrl());
		} catch (FTPLoginException ftpLoginException) {
			logger.error("No se puede loggear al FTP con el usuario {}", credentials.getUsername());
		} catch (FTPFileRemovalException e) {
			logger.error("No se puede borrar el archivo {} del FTP", ftpPath + "/" + fileFoundAtFtpPath);
		} catch (FTPNotFoundAnyFilesException e) {
			logger.warn("No se pudo encontrar ningun archivo en el FTP para el path {}", ftpPath);
		} catch (FTPRetrieveFileException e) {
			logger.error("No se puede traer el archivo {} del FTP", ftpPath + "/" + fileFoundAtFtpPath);
		} finally {
			onFinally(client);
		}
		return null;
	}
	
	@Override
	public List<String> copyLocalsAndRemoveCampaigns(String ftpPath,String tempPath, FTPCredentials credentials) {
		
		FTPClient client = null;
		String fileFoundForLog = null;
		List <String> filesFoundAtFtpPath = new ArrayList<String>();
		List <String> tempFilesPath = new ArrayList<String>();
		
		List<String> validCampaigns = firstProcessConfigurationService.getConfigurations(FirstProcessConfiguration.VALID_CAMPAIGN);
		try {
			client = connect(credentials);
			filesFoundAtFtpPath = getFilesNamesFromFTP(client, ftpPath);
			for (String fileFoundAtFtpPath : filesFoundAtFtpPath) {
				fileFoundForLog = fileFoundAtFtpPath;
				String countryName = getCountryNameFromFile(fileFoundAtFtpPath);
				if (!StringUtils.isEmpty(countryName)) {
					if (validCampaigns.contains(countryName)) {
							if (validCampaigns != null)
									 {copyLocal(client, tempPath, ftpPath,fileFoundAtFtpPath);
								deleteFileFromFtp(client, ftpPath,fileFoundAtFtpPath);
								tempFilesPath.add(tempPath + "/" + new File(fileFoundAtFtpPath)
												.getName());
						}
					}
				}
			}
			
			return tempFilesPath;
		} catch (FTPConnectionException ftpConnectionException) {
			logger.error("No se puede conectar al FTP {}", credentials.getFtpUrl());
		} catch (FTPLoginException ftpLoginException) {
			logger.error("No se puede loggear al FTP con el usuario {}", credentials.getUsername());
		} catch (FTPFileRemovalException e) {
			logger.error("No se puede borrar el archivo {} del FTP", ftpPath + "/" + fileFoundForLog);
		} catch (FTPNotFoundAnyFilesException e) {
			logger.warn("No se pudo encontrar ningun archivo en el FTP para el path {}", ftpPath);
		} catch (FTPRetrieveFileException e) {
			logger.error("No se puede traer el archivo {} del FTP", ftpPath + "/" + fileFoundForLog);
		} finally {
			onFinally(client);
		}
		return null;
	}

	private List<String> getFilesNamesFromFTP(FTPClient client, String ftpPath) throws FTPNotFoundAnyFilesException {
		FTPFile ftpFile = null;
		FTPFile[] ftpFiles = null;
		List<String> ftpFilesNames = new ArrayList<String>();
		try {
			ftpFiles = client.listFiles(ftpPath, ftpOnlyFilesFilter);
			if (ftpFiles != null && ftpFiles.length > 0) {
				logger.info("{} archivos encontrados en el FTP en {}", new Object[] {ftpFiles.length, ftpPath});
				for (int i = 0; i < ftpFiles.length; i++) {
					ftpFile = ftpFiles[i];
					ftpFilesNames.add(ftpFile.getName());
				}
			} else {
				throw new FTPNotFoundAnyFilesException();
			}
		} catch (IOException ioException){
			throw new FTPNotFoundAnyFilesException(ioException);
		}
		return ftpFilesNames;
	}

	private String getCountryNameFromFile(String filepath) {
		return new File(filepath).getName().split("\\d")[0];
	}

	private static FTPFileFilter ftpOnlyFilesFilter = new FTPFileFilter() {

		@Override
		public boolean accept(FTPFile file) {
			return file.isFile();
		}
		
	};
	
	private String getFirstFileFromFTP(FTPClient client, String ftpPath) throws FTPNotFoundAnyFilesException {
		if (StringUtils.isBlank(ftpPath)) {
			ftpPath = "/";
		}
		FTPFile ftpFile = null;
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = client.listFiles(ftpPath, ftpOnlyFilesFilter);
			if (ftpFiles != null && ftpFiles.length > 0) {
				logger.info("{} archivos encontrados en el FTP en {}", new Object[] {ftpFiles.length, ftpPath});
				ftpFile = ftpFiles[0];
			} else {
				throw new FTPNotFoundAnyFilesException();
			}
		} catch (IOException ioException){
			throw new FTPNotFoundAnyFilesException(ioException);
		}
		return ftpFile.getName();
	}
	
	private void copyLocal(FTPClient client, String tempPath, String ftpPath, String fileFoundAtFtpPath) throws FTPRetrieveFileException {
		String ftpFilepath = null;
		String tempFilePath = tempPath + "/" + new File(fileFoundAtFtpPath).getName();
		new File(tempPath).mkdir();
		if (ftpPath != null) {
			ftpFilepath = ftpPath + "/" + new File(fileFoundAtFtpPath).getName();
		} else {
			ftpFilepath = new File(fileFoundAtFtpPath).getName();
		}
		FileOutputStream tempFileOutputStream = null;
		try {
			tempFileOutputStream = new FileOutputStream(tempFilePath);
			if (!client.retrieveFile(ftpFilepath, tempFileOutputStream)) {
				throw new FTPRetrieveFileException();
			}
		} catch (IOException ioException) {
			throw new FTPRetrieveFileException(ioException);
		} finally {
			onFinally(null, tempFileOutputStream);
		}
	}

	private void deleteFileFromFtp(FTPClient client, String ftpPath, String fileFoundAtFtpPath) throws FTPFileRemovalException {
		String ftpFilepath = null;
		if(ftpPath != null) {
			ftpFilepath = ftpPath + "/" + new File(fileFoundAtFtpPath).getName();
		} else {
			ftpFilepath = new File(fileFoundAtFtpPath).getName();
		}
		try {
			if (!client.deleteFile(ftpFilepath)) {
				throw new FTPFileRemovalException();
			}
		} catch (IOException e) {
			throw new FTPFileRemovalException(e);
		}
	}

	@Override
	public boolean upload(String filename, String destinationPath, FTPCredentials credentials) {
		boolean couldUpload = false;
		
		FTPClient client = null;
	    FileInputStream fis = null;

	    try {
	    	client = connect(credentials);
	        int replay = client.getReplyCode();

	        if (FTPReply.isPositiveCompletion(replay)) {
			    File file = new File(filename);
			    fis = new FileInputStream(file);
		        client.setFileType(FTP.BINARY_FILE_TYPE);
		        String destinationFilePath = null;
		        if (destinationPath != null) {
		        	client.makeDirectory(destinationPath);
			        destinationFilePath = destinationPath + "/" + file.getName();
		        } else {
			        destinationFilePath = file.getName();
		        }
		        if (client.storeFile(destinationFilePath, fis)) {
		        	logger.info("Un archivo fue subido al FTP desde {} a {}.", new String[] {file.getAbsolutePath(), destinationFilePath});
		        	couldUpload = true;
		        }
	        }
	        
		} catch (SocketException e) {
			logger.error("No se pudo subir el archivo al FTP.", e);
		} catch (IOException e) {
			logger.error("No se pudo subir el archivo al FTP.", e);
		} catch (FTPConnectionException e) {
			logger.error("No se pudo subir el archivo al FTP.", e);
		} catch (FTPLoginException e) {
			logger.error("No se pudo subir el archivo al FTP.", e);
		} finally {
			onFinally(client, fis);
		}
	    return couldUpload;
	}

	private FTPClient connect(FTPCredentials credentials) throws FTPConnectionException, FTPLoginException {
		FTPClient client = new FTPClient();
		try {
			String[] url = credentials.getFtpUrl().split(":");
			Integer port = null;
			if (url.length == 2) {
				port = Integer.valueOf(url[1]);
			}
			if (port == null) {
				client.connect(credentials.getFtpUrl());
			} else {
				client.connect(url[0], port);
			}
			if (client.login(credentials.getUsername(), credentials.getPassword())) {
				logger.info("Coneccion exitosa al FTP a {}", credentials.getFtpUrl());
				return client;
			} else {
				throw new FTPLoginException();
			}
		} catch (IOException ioException) {
			throw new FTPConnectionException(ioException);
		}
	}

	private void onFinally(FTPClient client, Closeable... closables) {
		try {
			if (client != null) {
				client.logout();
				logger.info("Deslogeandose del FTP: {}", client.getRemoteAddress());
				client.disconnect();
				logger.info("FTP desconectado.");
			}
			if (closables != null) {
				for (Closeable closeable : closables) {
					if (closeable != null) {
						closeable.close();
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error tratando de cerrar la coneccion del FTP: {} / {}.", e.getClass().toString(), e.getMessage());
		}
	}
	
}

