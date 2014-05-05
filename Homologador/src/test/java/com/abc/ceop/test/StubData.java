package com.abc.ceop.test;

import java.util.Arrays;
import java.util.List;

import com.abc.ceop.model.entities.Configuration;
import com.abc.ceop.model.entities.FirstProcessConfigurationVariable;
import com.abc.ceop.model.entities.LocationSynonym;
import com.abc.ceop.model.entities.SecondProcessConfigurationVariable;

public class StubData {

	private StubData() {
	}

	public static List<FirstProcessConfigurationVariable> getFirstProcessConfigurationVariables() {
		return Arrays.asList(
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.INPUT_FTP_HOST, "127.0.0.1:6021", "FTP url for the CSV input file"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.INPUT_FTP_USERNAME, "inputguy", "Username for the input file FTP"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.INPUT_FTP_PASSWORD, "inputguy", "Password for the input file FTP"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.INPUT_FTP_PATH, "input", "FTP directory for the input file"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.OUTPUT_FTP_HOST, "127.0.0.1:6022", "FTP url for the CSV output file"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.OUTPUT_FTP_USERNAME, "outputguy", "Username for the output file FTP"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.OUTPUT_FTP_PASSWORD, "outputguy", "Password for the output file FTP"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.OUTPUT_FTP_PATH, "output", "FTP directory for the output file"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.LOCAL_PREFIX, "", "Prefix for allowing access to the phone network"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.INTERNATIONAL_PREFIX, "00", "Prefix for allowing access to international calls"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.TEMP_PATH, System.getenv("TEMP"), ""),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.SMTP_AUTH, "true", ""),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.SMTP_HOST, "smtp.gmail.com", ""),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.SMTP_PORT, "587", ""),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.SMTP_TLS, "true", ""),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.EMAIL_USER, "ceoptest@gmail.com"),
				new FirstProcessConfigurationVariable(Configuration.FirstProcessConfiguration.EMAIL_PASSWORD, "ceopceop"));
	}
	
	public static List<SecondProcessConfigurationVariable> getSecondProcessConfigurationVariables() {
		return Arrays.asList(
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.INPUT_FTP_HOST, "127.0.0.1", "FTP url for the CSV input file"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.INPUT_FTP_USERNAME, "root", "Username for the input file FTP"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.INPUT_FTP_PASSWORD, "root", "Password for the input file FTP"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.INPUT_FTP_PATH, "input", "FTP directory for the input file"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.OUTPUT_FTP_HOST, "127.0.0.1", "FTP url for the CSV output file"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.OUTPUT_FTP_USERNAME, "root", "Username for the output file FTP"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.OUTPUT_FTP_PASSWORD, "root", "Password for the output file FTP"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.OUTPUT_FTP_PATH, "output", "FTP directory for the output file"),
				new SecondProcessConfigurationVariable(Configuration.SecondProcessConfiguration.TEMP_PATH, System.getenv("TEMP"), "Folder to store temporary files"));
	}
	
	public static List<LocationSynonym> getSynonyms() {
		return Arrays.asList(new LocationSynonym("CAPITAL FEDERAL", "CACAL"));
	}
	
}
