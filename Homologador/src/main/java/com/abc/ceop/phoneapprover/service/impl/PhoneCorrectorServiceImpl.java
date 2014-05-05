package com.abc.ceop.phoneapprover.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.model.dto.FirstProcessCommonData;
import com.abc.ceop.model.entities.CellPhonePattern;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.phoneapprover.service.CellPhonePatternService;
import com.abc.ceop.phoneapprover.service.PhoneCorrectorService;

@Service
public class PhoneCorrectorServiceImpl implements PhoneCorrectorService {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(PhoneCorrectorServiceImpl.class);

	//Caso argentina unicamente con Dialed Direct seteado 
	private static final int CELLPHONE_PREFIX = 15;
	private static final int NATIONAL_PREFIX = 0;
	////
	
	
	
	private final CellPhonePatternService cellPhonePatternService;
	
	@Autowired
	public PhoneCorrectorServiceImpl(CellPhonePatternService cellPhonePatternService) {
		this.cellPhonePatternService = cellPhonePatternService;
	}
	
	@Override
	public boolean isCorrectable(String phone, Location location) {
		return phone.matches(".*?\\d{" + location.getCellPhoneDigitsCount() + "}")
				|| phone.matches(".*?\\d{" + location.getFixedPhoneDigitsCount() + "}");
	}
	
	private String getCellPhoneIndicator(List<CellPhonePattern> cellPhonePatterns, String phone, boolean isMobile) {
		if (cellPhonePatterns != null && !cellPhonePatterns.isEmpty()) {
			for (CellPhonePattern cellPhonePattern : cellPhonePatterns) {
				if(StringUtils.isNotBlank(cellPhonePattern.getPattern())) {
					String regexPattern = cellPhonePattern.getPattern()
							.replaceAll("x", "\\\\d")
							.replaceAll("\\*", ".*?");
					if (phone.matches(regexPattern)) {
						return cellPhonePattern.getCellIndicator();
					}
				}
			}
		}
		if(isMobile) {
			if(cellPhonePatterns != null &&
					cellPhonePatterns.size() > 0 &&
					cellPhonePatterns.get(0) != null &&
					StringUtils.isNotBlank(cellPhonePatterns.get(0).getCellIndicator())) {
				return cellPhonePatterns.get(0).getCellIndicator();
			}
		}
		return "";
	}

	@Override
	public String easyCorrect(String phone, Country country, FirstProcessCommonData commonData, boolean isMobile) {
		if(phone != null &&
				((!isMobile && phone.length() == country.getFixPhoneLength()) ||
				 (isMobile && phone.length() == country.getCellPhoneLength()))) {
			String localPrefix = commonData.getLocalPrefix();
			localPrefix = localPrefix != null? localPrefix : "";
			StringBuilder correctedPhone = new StringBuilder(localPrefix);
			correctedPhone.append(commonData.getInternationalPrefix());
			correctedPhone.append(country.getInternationalCode());
			correctedPhone.append(phone);
			return correctedPhone.toString();
		}
		return null;
	}

	private String validatePhone(String cellPhoneIndicator, String phone, Location location, List<Location> locationsForcountry, 
			Integer maxNationalCodeLength, FirstProcessCommonData commonData) {
		String phoneNumber = null;
		boolean iscell = false;
		int pos15 = 0;
				
		if(StringUtils.isNumeric(phone)) {
			phoneNumber = phone.replaceFirst("^0+(?!$)", "");
		}
		
		if(phoneNumber != null &&
				phoneNumber.startsWith(location.getCountry().getInternationalCode())) {
			phoneNumber = phoneNumber.substring(location.getCountry().getInternationalCode().length(), phoneNumber.length());
		}
		
		if(StringUtils.isNotBlank(cellPhoneIndicator) &&
				phoneNumber != null &&
				phoneNumber.startsWith(cellPhoneIndicator)) {
			phoneNumber = phoneNumber.substring(cellPhoneIndicator.length(), phoneNumber.length());
			iscell = true;
		}
		
		// Es ARGENTINA remuevo el  15
		if(location.getCountry().getId() == 1) {
			if(phoneNumber != null &&
					phoneNumber.startsWith("15")) {
				iscell = true;
				phoneNumber = phoneNumber.replaceFirst("15", "");
				if(phoneNumber.length() == 8) {
					phoneNumber = "11" + phoneNumber;
				}
			} else {
				if(location.getCountry().getCellPhoneLength() == phoneNumber.length() - 2) {
					pos15 = phoneNumber.indexOf("15");
					if(pos15 <= maxNationalCodeLength) {
						iscell = true;
							phoneNumber = phoneNumber.replaceFirst("15", "");
					}
				}
			}
		}
		
		if(iscell) {
			if(location.getCountry().getCellPhoneLength() == phoneNumber.length()) {
				if(location.getCountry().getId() == 1) {
					List<String> list = new ArrayList<String>();
					for (int i = 1 ; i <= maxNationalCodeLength ; i++) {
						list.add(phoneNumber.substring(0,i));
					}
					if(!searchForNationalcode(locationsForcountry, list)) {
						return null;
					}
				}
				if (!commonData.isDialedDirect()|| !"ARGENTINA".equals(location.getCountry().getName())) {
					return commonData.getInternationalPrefix() + location.getCountry().getInternationalCode()
							+ cellPhoneIndicator + phoneNumber;
				} else{
					
					String prefix = phoneNumber.substring(0, pos15);
					String sufix = 	phoneNumber.substring(pos15,phoneNumber.length());
										
					if (phoneNumber.startsWith("11")) {
						phoneNumber = phoneNumber.substring(2,
								phoneNumber.length());
						return CELLPHONE_PREFIX + phoneNumber;
					} else {
						return NATIONAL_PREFIX + prefix + CELLPHONE_PREFIX + sufix;
					}
				}
			}
		} else {
			if(location.getCountry().getFixPhoneLength() == phoneNumber.length()) {
				List<String> list = new ArrayList<String>();
				for (int i = 1 ; i <= maxNationalCodeLength ; i++) {
					list.add(phoneNumber.substring(0,i));
				}
				if(!searchForNationalcode(locationsForcountry, list)) {
					return null;
				}
				if (!commonData.isDialedDirect() || !"ARGENTINA".equals(location.getCountry().getName())) {
				return commonData.getInternationalPrefix() + location.getCountry().getInternationalCode() + phoneNumber;
				}
				else if (phoneNumber.startsWith("11")) {
					phoneNumber = phoneNumber.substring(2, phoneNumber.length());
					return phoneNumber;
				}
				else {
					return NATIONAL_PREFIX + phoneNumber;
					}
			}
		}
		return null;
	}
	
	private boolean searchForNationalcode(List<Location> locationsForcountry, List<String> list) {
		for (String nationalCode : list) {
			for (Location location : locationsForcountry) {
				if(location != null &&
						location.getNationalCode() != null &&
						location.getNationalCode().equals(nationalCode)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String correct(String phone, Location location, FirstProcessCommonData commonData, boolean isMobile) {
				
		List<CellPhonePattern> cellPhonePatterns = cellPhonePatternService.getMostSpecificPatternAction(location);
		String cellPhoneIndicator = getCellPhoneIndicator(cellPhonePatterns, phone, isMobile);
	
		String validPhone = validatePhone(cellPhoneIndicator, phone, location, commonData.getLocationsForcountry(), 
				commonData.getMaxNationalCodeLength(), commonData);
		if(validPhone != null) {
			return validPhone;
		}
		
		if (commonData.isDialedDirect() && "ARGENTINA".equals(location.getCountry().getName())) {

			
			String localPrefix = commonData.getLocalPrefix();
			localPrefix = localPrefix != null? localPrefix : "";
			StringBuilder correctedPhone = new StringBuilder(localPrefix);
			if (StringUtils.isNotBlank(cellPhoneIndicator)) {
				correctedPhone.append(cellPhoneIndicator);
				}
			correctedPhone.append(location.getNationalCode());
			
			if (StringUtils.isNotBlank(cellPhoneIndicator)) {
				if(phone.length() - Integer.parseInt(location.getCellPhoneDigitsCount()) >= 0 &&
						phone.length() - Integer.parseInt(location.getCellPhoneDigitsCount()) <= phone.length()) {
					correctedPhone.append(phone.substring(phone.length() - Integer.parseInt(location.getCellPhoneDigitsCount()),
							phone.length()));
				} else {
					System.out.println("Error en: " + phone + " | " + location.getCellPhoneDigitsCount());
					return null;
				}
			} else {
				if(phone.length() - Integer.parseInt(location.getFixedPhoneDigitsCount()) >= 0 &&
						phone.length() - Integer.parseInt(location.getFixedPhoneDigitsCount()) <= phone.length()) {
					correctedPhone.append(phone.substring(phone.length() - Integer.parseInt(location.getFixedPhoneDigitsCount()),
							phone.length()));
				} else {
					System.out.println("Error en: " + phone + " | " + location.getFixedPhoneDigitsCount());
					return null;
				}
			}
			String correctedPhoneToString = correctedPhone.toString();
			
			if (correctedPhoneToString.startsWith("11")){
				correctedPhoneToString = correctedPhoneToString.substring(2 , correctedPhoneToString.length());
				return correctedPhoneToString;
				}
				if (!cellPhoneIndicator.isEmpty()  && correctedPhoneToString.startsWith(cellPhoneIndicator)){
					correctedPhoneToString = correctedPhoneToString.substring(1, correctedPhoneToString.length());
					return NATIONAL_PREFIX + correctedPhoneToString;
				}
				else{
					return NATIONAL_PREFIX + correctedPhoneToString;
					}
						
		} else {
						
		String localPrefix = commonData.getLocalPrefix();
		localPrefix = localPrefix != null? localPrefix : "";
		StringBuilder correctedPhone = new StringBuilder(localPrefix);
		correctedPhone.append(commonData.getInternationalPrefix());
		correctedPhone.append(location.getCountry().getInternationalCode());
		
		if (StringUtils.isNotBlank(cellPhoneIndicator)) {
			
			if ("ARGENTINA".equals(location.getCountry().getName())) {
			    correctedPhone.append(cellPhoneIndicator);
			    correctedPhone.append(location.getNationalCode());
			} else if ("SURINAME".equals(location.getCountry().getName())) {
			    correctedPhone.append(location.getNationalCode()); 
			    correctedPhone.append(cellPhoneIndicator);
			} else { 
			    correctedPhone.append(cellPhoneIndicator);
			 }
		} else {
			correctedPhone.append(location.getNationalCode());
		}
		
		if (StringUtils.isNotBlank(cellPhoneIndicator)) {
			if(phone.length() - Integer.parseInt(location.getCellPhoneDigitsCount()) >= 0 &&
					phone.length() - Integer.parseInt(location.getCellPhoneDigitsCount()) <= phone.length()) {
				correctedPhone.append(phone.substring(phone.length() - Integer.parseInt(location.getCellPhoneDigitsCount()),
						phone.length()));
			} else {
				System.out.println("Error en: " + phone + " | " + location.getCellPhoneDigitsCount());
				return null;
			}
		} else {
			if(phone.length() - Integer.parseInt(location.getFixedPhoneDigitsCount()) >= 0 &&
					phone.length() - Integer.parseInt(location.getFixedPhoneDigitsCount()) <= phone.length()) {
				correctedPhone.append(phone.substring(phone.length() - Integer.parseInt(location.getFixedPhoneDigitsCount()),
						phone.length()));
			} else {
				System.out.println("Error en: " + phone + " | " + location.getFixedPhoneDigitsCount());
				return null;
			}
		}
		return correctedPhone.toString();
		    }
		}
	}

	
	


