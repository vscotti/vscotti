package com.abc.ceop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CampaignUtils {

	public static String getCampaignIdTrabajo(String excelFilePath) {
		String campaignIdTrabajo = "";
		String regex = "\\b[a-zA-Z]*(\\d+)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(excelFilePath);
		if (matcher.find()) {
			campaignIdTrabajo = matcher.group(1);
		} else {
			return null;
		}
		return campaignIdTrabajo;
	}
	
	public static String getCampaignDate(String excelFilePath) {
		String campaignDate = "";
		Pattern pattern = Pattern.compile("(\\d{6,8})+?");
		Matcher matcher = pattern.matcher(excelFilePath);
		if (matcher.find()) {
			campaignDate = matcher.group(1);
		} else {
			return campaignDate;
		}
		return campaignDate;
	}
	
}
