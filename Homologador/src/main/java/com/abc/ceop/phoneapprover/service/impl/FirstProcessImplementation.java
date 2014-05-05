package com.abc.ceop.phoneapprover.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.common.service.CellPhoneConfigurationService;
import com.abc.ceop.common.service.ConditionService;
import com.abc.ceop.common.service.CountryService;
import com.abc.ceop.common.service.CsvFileConfigurationService;
import com.abc.ceop.common.service.EmailLogsService;
import com.abc.ceop.common.service.FTPService;
import com.abc.ceop.common.service.LocationSearchConfigService;
import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.common.service.RecordDetailService;
import com.abc.ceop.dao.GenericDao;
import com.abc.ceop.model.dto.FTPCredentials;
import com.abc.ceop.model.dto.FirstProcessCommonData;
import com.abc.ceop.model.dto.FirstProcessResultInfo;
import com.abc.ceop.model.dto.FirstProcessStats;
import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Configuration;
import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.model.entities.EmailLogs;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.model.entities.LocationSearchConfig;
import com.abc.ceop.model.entities.RecordDetail;
import com.abc.ceop.phoneapprover.service.FirstProcess;
import com.abc.ceop.phoneapprover.service.FirstProcessConfigurationService;
import com.abc.ceop.phoneapprover.service.FirstProcessEmailService;
import com.abc.ceop.phoneapprover.service.PhoneCorrectorService;
import com.abc.ceop.phoneapprover.service.PhonesCsvFileReader;
import com.abc.ceop.phoneapprover.service.PhonesExcelFileCreator;


@Service
public class FirstProcessImplementation implements FirstProcess {

	private final Logger logger = LoggerFactory.getLogger(FirstProcessImplementation.class);
	
	private final PhonesCsvFileReader phonesCsvFileReader;
	private final PhoneCorrectorService phoneCorrector;
	private final PhonesExcelFileCreator phonesExcelFileCreator;
	private final FTPService ftpService;
	private final FirstProcessConfigurationService configurationService;
	private final CsvFileConfigurationService csvFileConfigurationService;
	private final CampaignService campaignService;
	private final LocationService locationService;
	private final FirstProcessEmailService emailService;
	private final FirstProcessEmailService emailServiceSecondEmail;
	private final RecordDetailService recordDetailService;
	private final ConditionService conditionService;
	private final GenericDao genericDao;
	private final EmailLogsService emailLogsService;
	private final CountryService countryService;
	private final CellPhoneConfigurationService cellPhoneConfigurationService;
	private FirstProcessResultInfo info = null;
	private FirstProcessResultInfo secondInfo = null;
	private FirstProcessCommonData commonData = null;
	private FirstProcessCommonData secondCommnoData = null;
	private final LocationSearchConfigService locationSearchConfigService;
	
	@Autowired
	public FirstProcessImplementation(PhonesCsvFileReader phonesCsvFileReader,
			PhoneCorrectorService phoneCorrector,
			PhonesExcelFileCreator phonesExcelFileCreator,
			FTPService ftpService, FirstProcessConfigurationService configurationService,
			CsvFileConfigurationService csvFileConfigurationService, CampaignService campaignService,
			LocationService locationService, FirstProcessEmailService emailService,
			FirstProcessEmailService emailServiceSecondEmail, ConditionService conditionService,
			RecordDetailService recordDetailService, GenericDao genericDao, 
			EmailLogsService emailLogsService,
			CountryService countryService,
			CellPhoneConfigurationService cellPhoneConfigurationService,
			LocationSearchConfigService locationSearchConfigService) {
		
		super();
		this.phonesCsvFileReader = phonesCsvFileReader;
		this.phoneCorrector = phoneCorrector;
		this.phonesExcelFileCreator = phonesExcelFileCreator;
		this.ftpService = ftpService;
		this.configurationService = configurationService;
		this.csvFileConfigurationService = csvFileConfigurationService;
		this.campaignService = campaignService;
		this.locationService = locationService;
		this.emailService = emailService;
		this.emailServiceSecondEmail = emailServiceSecondEmail;
		this.recordDetailService = recordDetailService;
		this.genericDao = genericDao;
		this.conditionService = conditionService;
		this.emailLogsService = emailLogsService;
		this.countryService = countryService;
		this.cellPhoneConfigurationService = cellPhoneConfigurationService;
		this.locationSearchConfigService = locationSearchConfigService;
	}
		
	@Override
	@Transactional(readOnly = false)
	public void execute() {
		logger.info("Inicializando Primer proceso. Version 06/11/2012");
		List<String> filesNames = ftpService.copyLocalsAndRemoveCampaigns(
						configurationService.getConfigurationValue(FirstProcessConfiguration.INPUT_FTP_PATH),
						configurationService.getConfigurationValue(FirstProcessConfiguration.TEMP_PATH),
						getFTPInputCredentials());
		if (filesNames != null) {
			for (String fileName : filesNames) {
				List<String> excelFilepaths = executeCorrection(fileName);
				if (excelFilepaths != null) {
					for (String excelFilepath : excelFilepaths) {
						uploadFileToFPT(excelFilepath);
					}
				}
				if (commonData != null) {
					info.setFileName(fileName);
					checkMinPhoneCount(info);
					emailService.sendEmail(info, false, false);
				}
			}
		}
		checkTimeWithoutData(secondInfo);
		logger.info("Proceso finalizado");
	}

	
	private void checkTimeWithoutData(FirstProcessResultInfo secondInfo) {

		logger.info("Empezando Proceso de bases faltantes...");
		
		Date today = new Date();
		List<Campaign> allCampaigns = campaignService.getAll();
		
		for (Campaign campaign : allCampaigns) {
			logger.info("Procesando campaña: " + campaign.getCampaign());
			secondCommnoData = loadCommonData(campaign, null, true);
			secondInfo = new FirstProcessResultInfo();
			secondInfo.setCampaignCountry(campaign);
			secondInfo.setAveraragePhones(-1);
//			listUncontacted (secondInfo);

			if ((secondCommnoData.getLastSentMail() != null && secondCommnoData.getTimeWithoutData() != null
					&& (today.getTime() - secondCommnoData.getLastSentMail().getTime()) / 1000 / 60 / 60 >= secondCommnoData.getTimeWithoutData()) ||
					secondCommnoData.getLastSentMail() == null) {
				if (secondCommnoData.getLastDateForCountry() != null && secondCommnoData.getTimeWithoutData() != null
					&& (today.getTime() - secondCommnoData.getLastDateForCountry().getTime()) / 1000 / 60 / 60 >= secondCommnoData.getTimeWithoutData()) {
					secondInfo.setNoDBDetected(secondCommnoData.getTimeWithoutData());
					secondCommnoData.setLastSentMail(today);
					EmailLogs emailLogs = new EmailLogs(today, campaign);
					genericDao.update(emailLogs);
					boolean hasNodb = true;
					logger.info("Enviando email de notificacion");
					emailServiceSecondEmail.sendEmail(secondInfo, hasNodb, false);
					logger.info("Email enviado");
				}
			}
		}
		logger.info("Proceso finalizado...");
		
	}

//	private void listUncontacted(FirstProcessResultInfo secondInfo) {
//		
//		//Setear la hora en que el proceso tiene que correr.
//		
//		int hour = 01;
//		int minutes = 59;
//		int second = 59;
//		
//		Date dateTo = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(dateTo);
//		calendar.set(Calendar.HOUR_OF_DAY, hour);
//		calendar.set(Calendar.MINUTE, minutes);
//		calendar.set(Calendar.SECOND, second);
//
//		calendar.add(Calendar.DATE, -1);
//		Date dateFrom = calendar.getTime();
//			
//		List<RecordDetail> uncontactedPhones = new ArrayList<RecordDetail>();
//		uncontactedPhones= recordDetailService.getRecordDetailUncontacted(dateFrom, dateTo, secondInfo.getCampaignCountry().getCountry(), 0);
//		
//		
//	}

	private void checkMinPhoneCount(FirstProcessResultInfo inf) {

		inf.setNoDBDetected(-1);

		if ((commonData.getMinPhoneCount() != null && inf.getStats() != null && inf.getStats().getCorrectedPhonesCount() < commonData.getMinPhoneCount())
				|| inf.isNoGeneratedFile() && commonData.getMinPhoneCount() != 0 && commonData.getMinPhoneCount() != null)  {

			inf.setAveraragePhones(commonData.getMinPhoneCount());
			boolean hasBelowAverager = true;
			emailServiceSecondEmail.sendEmail(inf, false, hasBelowAverager);
		}
		
	}
	
	private String getCountryNameFromFile(String filepath) {
		return new File(filepath).getName().split("\\d")[0];
	}
	
	private String getCampaignDateFromFile(String filepath) {
		int countryNameLength = getCountryNameFromFile(filepath).length();
		return new File(filepath).getName().substring(countryNameLength, countryNameLength + 8);
	}
	
	private boolean searchRecord(List<RecordDetail> recordDetails, String phone) {
	    logger.info("Verificando Tel Duplicado " + phone);
		if(recordDetails != null) {
			for (RecordDetail recordDetail : recordDetails) {
				Long phonenumberDB = 0L;
				if(recordDetail != null &&
						recordDetail.getPhone() != null &&
						StringUtils.isNumeric(recordDetail.getPhone())) {
					phonenumberDB = Long.parseLong(recordDetail.getPhone());
				}
				Long phonenumber = -1L;
				if(phone != null &&
						StringUtils.isNumeric(phone)) {
					phonenumber = Long.parseLong(phone);
				}
				if(phonenumberDB.longValue() == phonenumber.longValue()) {
				    logger.info("Telefono Duplicado: Tel en Base " + phonenumberDB + "  Tel del txt " + phonenumber.longValue() ); 
					return true;
				}
			}
		}
		logger.info("Este tel " + phone + "  no se encontro en RecordDetail");
		return false;
	}
	
	private List<Record> removeDuplicated(List<Record> records, String countryCampaign) {
		List<Record> list = new ArrayList<Record>();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date date = c.getTime();
		
		List<RecordDetail> recordDetails = recordDetailService.getRecordDetails(date, new Date(), countryCampaign);

		for (Record record : records) {
		   		       
			boolean hasValues = false;
			if(record.getTelephones() != null) {
				List<String> phones = new ArrayList<String>();
				for (String phone : record.getTelephones()) {
				  
					if (!searchRecord(recordDetails, phone)) {
						phones.add(phone);
						hasValues = true;
					
					}
				}
				
				record.setTelephones(phones);
			}
			if(record.getMobile() != null) {
				if (searchRecord(recordDetails, record.getMobile())) {
					record.setMobile(null);
				} else {
					hasValues = true;
				}
			}
			if(hasValues) {
				list.add(record);
			}
		}
		return list;
	}
	
	private FirstProcessCommonData loadCommonData(Campaign campaign, Country country, boolean isNoDBCheck) {
		Country countryDB = country;
		if(campaign.getCountry_id() != null) {
			countryDB = countryService.getCountryById(campaign.getCountry_id());
		}
		FirstProcessCommonData cd = new FirstProcessCommonData();
		if(!isNoDBCheck) { 
			cd.setMaxNationalCodeLength(locationService.getMaxNationalCodeLenght(countryDB));
			cd.setLocalPrefix(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.LOCAL_PREFIX));
			cd.setInternationalPrefix(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INTERNATIONAL_PREFIX));
			cd.setLocationsForcountry(locationService.getAllForCountry(countryDB));
			cd.setMinPhoneCount(conditionService.getMinPhoneCountValue(campaign));
			cd.setDialedDirect(Boolean.parseBoolean(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.DIALED_DIRECT)));
			cd.setCellPhoneConfigurationForCountry(cellPhoneConfigurationService.getCellPhoneConfigurationByCountrry(countryDB));
		} else {
			cd.setLastSentMail(emailLogsService.getLastSentMail(campaign));
			cd.setTimeWithoutData(conditionService.getTimeWithOutDataValue(campaign));
			cd.setLastDateForCountry(recordDetailService.getLastDateForCountry(campaign.getCountry()));
		}
		

		return cd;
	}
	
	@Transactional(readOnly = false)
	public List<String> executeCorrection(String filepath) {
		info = new FirstProcessResultInfo();
		commonData = null;
		String countryName = getCountryNameFromFile(filepath);
		CsvFileConfiguration csvFileConfiguration = csvFileConfigurationService.getCsvFileConfiguration(countryName);
		if(csvFileConfiguration == null) {
			System.out.println("Falta configurtacion para campaña. Agregar datos a la tabla CsvFileConfiguration.");
			logger.error("Falta configurtacion para campaña. Agregar datos a la tabla CsvFileConfiguration. Archivo: {}", filepath);
			return null;
		}
		
		List<Record> unprocessedRecords = phonesCsvFileReader.readFile(filepath, csvFileConfiguration);
		 	
		Campaign campaign = campaignService.getCampaignName(countryName);
		if(campaign == null) {
			List<String> list = new ArrayList<String>();
			Map<String, List<Record>> countrryMap = generateRecordsByCountry(unprocessedRecords);
			if(!countrryMap.keySet().isEmpty()) {
				for (String countryNameKey : countrryMap.keySet()) {
					boolean existCountry = false;
					Country country = countryService.getCountryByName(countryNameKey);
					if(country != null &&
							country.getName() != null &&
							country.getName().equals(countryNameKey)) {
						existCountry = true;
						String shortCountryName = countryNameKey.substring(0, 3);
						campaign = campaignService.getCampaignName(shortCountryName);
						String str = createExcelFilePath(campaign, csvFileConfiguration, filepath);
						str.toString();
						if(campaign != null) {
							String file = processData(countrryMap.get(countryNameKey), campaign, country, csvFileConfiguration, filepath, shortCountryName);
							if(file != null) {
								list.add(file);
							}
						} else {
							System.out.println("Hay una Campaña invalida. Agregar datos a la tabla Campaign para " + shortCountryName);
							logger.error("Campaña invalida. Agregar datos a la tabla Campaign. Archivo: {}", filepath);
						}
					}
					if(!existCountry) {
						System.out.println("Falta configurtacion de pais en base de datos. Agregar datos a la tabla Country.");
						logger.error("Falta configurtacion de pais en base de datos. Agregar datos a la tabla Country. Archivo: {}", filepath);
					}
				}
				if(!list.isEmpty()) {
					return list;
				}
				return null;
			} else {
				System.out.println("Hay una Campaña invalida. Agregar datos a la tabla Campaign para " + countryName);
				logger.error("Campaña invalida. Agregar datos a la tabla Campaign. Archivo: {}", filepath);
				return null;
			}
		} else {
			String file = processData(unprocessedRecords, campaign, null, csvFileConfiguration, filepath, countryName);
			if(file != null) {
				List<String> list = new ArrayList<String>();
				list.add(file);
				return list;
			}
			return null;
		}
	}
	
	private Map<String, List<Record>> generateRecordsByCountry(List<Record> records) {
		Map<String, List<Record>> map = new HashMap<String, List<Record>>();
		for (Record record : records) {
			if(record.getLocation() != null &&
					record.getLocation().getCountry() != null &&
					record.getLocation().getCountry().getName() != null) {
				if(map.containsKey(record.getLocation().getCountry().getName())) {
					List<Record> list = map.get(record.getLocation().getCountry().getName());
					list.add(record);
				} else {
					List<Record> list = new ArrayList<Record>();
					list.add(record);
					map.put(record.getLocation().getCountry().getName(), list);
				}
			}
		}
		return map;
	}
	
	private String processData(List<Record> unprocessedRecords, 
							   Campaign campaign, 
							   Country country,
							   CsvFileConfiguration csvFileConfiguration,
							   String filepath, 
							   String countryName) {
	
		commonData = loadCommonData(campaign, country, false);

		info.setCampaignCountry(campaign);
		info.setNoGeneratedFile(false);
		
		
		List<Record> validRecords = tryValidating(filepath, unprocessedRecords, commonData, info, campaign);

		validRecords = removeDuplicated(validRecords, countryName);
		
		if (validRecords != null && validRecords.size() > 0) {
			String excelFilepath = phonesExcelFileCreator.createPhonesExcelFile(
					createExcelFilePath(campaign, csvFileConfiguration, filepath), validRecords);
			if (excelFilepath != null) {
				logger.info("Archivo Excel creado en {}.", new File(excelFilepath).getAbsolutePath());
				tryStoringRecordDetails(validRecords, countryName, filepath);
			} else {
				logger.error("No se pudo crear el archivo Excel para el archivo {}.", filepath);
				info.setResult("No se pudo crear el archivo Excel para el archivo " + filepath);
				info.setNoGeneratedFile(true);
				return null;
			}

			return excelFilepath;
		} else {
			logger.warn("No se pudo generar el archivo Excel para el archivo {}.", filepath);
			info.setResult("No se pudo generar el archivo Excel para el archivo " + filepath);
			info.setNoGeneratedFile(true);
			return null;
		}
	}

	private Map<String, Boolean> generateSearchLocationMap(LocationSearchConfig locationSearchConfig) {
		
		Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
		map.put("country", locationSearchConfig.isCountry());
		map.put("state", locationSearchConfig.isState());
		map.put("largeCity", locationSearchConfig.isLargeCity());
		map.put("smallCity", locationSearchConfig.isSmallCity());
		
		return map;
	}
	
	private void tryStoringRecordDetails(List<Record> validRecords, String countryCampaign, String filepath) {
//		Calendar c = Calendar.getInstance();
//		c.setTime(new Date());
//		c.set(Calendar.HOUR_OF_DAY, 0);
//		c.set(Calendar.MINUTE, 0);
//		c.set(Calendar.SECOND, 0);
//		c.set(Calendar.MILLISECOND, 0);
//		Date date = c.getTime();
		Date date = new Date();
		String fileName = new File(filepath).getName(); 
//		int contacted = 0;
				
		for (Record record : validRecords) {
			for (String phone : record.getTelephones()) {
				phone = phone != null? phone.replaceAll("[^\\d]", "") : null;
				RecordDetail recordDetail = new RecordDetail(phone, date, record.getHeader(), record.getRowValues(), countryCampaign, record.getSuscriber(), fileName, record.getEmail() );
//				RecordDetail recordDetail = new RecordDetail(phone, date, record.getHeader(), record.getRowValues(), countryCampaign, record.getSuscriber(), fileName, contacted);

				genericDao.update(recordDetail);
			}
			if(record != null &&
					record.getMobile() != null) {
				String mobile = record.getMobile() != null? record.getMobile().replaceAll("[^\\d]", "") : null;
				RecordDetail recordDetail = new RecordDetail(mobile, date, record.getHeader(), record.getRowValues(), countryCampaign, record.getSuscriber(), fileName, record.getEmail());
//				RecordDetail recordDetail = new RecordDetail(mobile, date, record.getHeader(), record.getRowValues(), countryCampaign, record.getSuscriber(), fileName, contacted);

				genericDao.update(recordDetail);
			}
		}
	}
	
	private void uploadFileToFPT(String excelFilepath) {
		if (ftpService.upload(excelFilepath,
				configurationService.getConfigurationValue(FirstProcessConfiguration.OUTPUT_FTP_PATH),
				getFTPOutputCredentials())) {
			FileUtils.deleteQuietly(new File(excelFilepath));
			logger.info("Archivo Excel {} fue eliminado.", new File(excelFilepath).getAbsolutePath());
			info.setResult("El archivo " + new File(excelFilepath).getName() + " se subio al FTP correctamente");
		} else {
			logger.error("No se pudo subir el archivo excel {} al FTP.", new File(excelFilepath).getAbsolutePath());
			info.setResult("No se pudo subir el archivo " + excelFilepath + " excel al FTP");
		}
	}
	
	private FTPCredentials getFTPInputCredentials() {
		return new FTPCredentials(
				configurationService.getConfigurationValue(FirstProcessConfiguration.INPUT_FTP_HOST),
				configurationService.getConfigurationValue(FirstProcessConfiguration.INPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(FirstProcessConfiguration.INPUT_FTP_PASSWORD));
	}
	
	private FTPCredentials getFTPOutputCredentials() {
		return new FTPCredentials(
				configurationService.getConfigurationValue(FirstProcessConfiguration.OUTPUT_FTP_HOST),
				configurationService.getConfigurationValue(FirstProcessConfiguration.OUTPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(FirstProcessConfiguration.OUTPUT_FTP_PASSWORD));
	}
	
	private String createExcelFilePath(Campaign campaign, CsvFileConfiguration csvFileConfiguration, String filepath) {
		String campaignDate = getCampaignDateFromFile(filepath);
		if(csvFileConfiguration != null &&
				csvFileConfiguration.getDateFormat() != null &&
				campaignDate != null &&
				campaignDate.length() == 8) {
			int monthPos = csvFileConfiguration.getDateFormat().indexOf("MM");
			int yearPos = csvFileConfiguration.getDateFormat().indexOf("YYYY");
			int dayPos = csvFileConfiguration.getDateFormat().indexOf("DD");
			if(monthPos != -1 &&
					yearPos != -1 &&
					dayPos != -1) {
				campaignDate = campaignDate.substring(yearPos, yearPos + 4) +
							   campaignDate.substring(monthPos, monthPos + 2) +
							   campaignDate.substring(dayPos, dayPos + 2);
			}
		}
		String currentDate = new SimpleDateFormat("hh-mm-ss").format(new Date());
		return configurationService.getConfigurationValue(FirstProcessConfiguration.TEMP_PATH)
				+ "/" + "C" + campaign.getCampaign() + "-"
				+ campaignDate + "__" + currentDate + "_Contacts" + ".xls";
	}

	private String validateMobilePhone(String mobilePhone, 
											Location validLocation, 
											FirstProcessStats stats,
											FirstProcessCommonData commonData, 
											Set<String> phonesSet) {
		String validPhone = null;
		mobilePhone = mobilePhone != null? mobilePhone.replaceAll( "[^\\d]", "" ) : null;
		if (phoneCorrector.isCorrectable(mobilePhone, validLocation)) {
			String correctedPhone = phoneCorrector.correct(mobilePhone, validLocation, commonData, true);
			if(correctedPhone != null) {
				if (phonesSet.add(correctedPhone)) {
					validPhone = correctedPhone;
				} else {
					stats.incrementDuplicatedPhonesCount();
				}
			}
			stats.incrementCorrectedPhonesCount();
			logger.info("El telefono {} no es valido para la location {}. Fue corregido a {}.", new Object[] {mobilePhone, validLocation, correctedPhone});
		} else {
			stats.incrementInvalidPhonesCount();
			logger.warn("El telefono {} no es valido y no pudo se corregido para la location {}.", new Object[] {mobilePhone, validLocation});
		}
		return validPhone;
	}

	private List<String> validatePhones(List<String> telephones, 
										Location validLocation, 
										FirstProcessStats stats,
										FirstProcessCommonData commonData, 
										Set<String> phonesSet) {
		List<String> validPhones = new ArrayList<String>();
		for (String phone : telephones) {
			phone = phone != null? phone.replaceAll( "[^\\d]", "" ) : "";
			if (phoneCorrector.isCorrectable(phone, validLocation)) {
				String correctedPhone = phoneCorrector.correct(phone, validLocation, commonData, false);
				if(correctedPhone != null) {
					if (phonesSet.add(correctedPhone)) {
						validPhones.add(correctedPhone);
					} else {
						stats.incrementDuplicatedPhonesCount();
					}
					stats.incrementCorrectedPhonesCount();
					logger.info("El telefono {} no es valido para la location {}. Fue corregido a {}.", new Object[] {phone, validLocation, correctedPhone});
				}
			} else {
				if(StringUtils.isNotBlank(phone)) {
					stats.incrementInvalidPhonesCount();
					logger.warn("El telefono {} no es valido y no pudo ser corregido para la location {}.", new Object[] {phone, validLocation});
				}
			}
		}
		return validPhones;
	}
 
	//metodo para buscar localidades que fue reemplazado por searchLoactionbyMap 

	//	private Location searchLoaction(List<Location> locationsForcountry, Location location) {
//		if(location != null) {
//			for (Location locDB : locationsForcountry) {
//				if(locDB != null &&
//						locDB.equals(location)) {
//					return locDB;
//				}
//			}
//		}
//		return null;
//	}
	
	private Location searchLoactionbyMap (List<Location> locationsForcountry, Location location, Map <String, Boolean> searchLocationMap ) {
		
		if(searchLocationMap.get("country") == false && 
		   searchLocationMap.get("state") == false &&
		   searchLocationMap.get("largeCity") == false &&
		   searchLocationMap.get("smallCity") == false ) {
			return null;
				}
		
		if(location != null ) {
			for (Location locDB : locationsForcountry) {
				if(locDB != null &&
						locDB.compareWith(location, searchLocationMap)) {
					return locDB;
				}
			}
		}
		return null;
	}
	
	
	private List<Record> tryValidating(String filepath, 
									   List<Record> unprocessedRecords, 
									   FirstProcessCommonData commonData,
									   FirstProcessResultInfo info, Campaign campaign) {
		
		List<Record> validRecords = new ArrayList<Record>();
		Set<String> phonesSet = new HashSet<String>();

		LocationSearchConfig locationSearchConfig =  locationSearchConfigService.getLocationSearchConfig(campaign.getCountry());
		Map<String, Boolean> searchLocationMap = generateSearchLocationMap(locationSearchConfig);
		
		if (searchLocationMap.get("country")== false && searchLocationMap.get("state")==false && searchLocationMap.get("largeCity")== false &&
				searchLocationMap.get("smallCity") == false){				
		logger.error("La tabla en proceso en LocationSearchConfiguration esta toda configurada en false y no permite la correccion", info.getStats() );
		}
		
		for (Record record : unprocessedRecords) {
			Location location = searchLoactionbyMap(commonData.getLocationsForcountry(), record.getLocation(), searchLocationMap);
//			Location location = searchLoaction(commonData.getLocationsForcountry(), record.getLocation());
			if (location != null) {
				if (!location.equals(record.getLocation())) {
					logger.info("Location {} interpreted as location {}", new Object[] {record.getLocation(), location});
				}
				List<String> validPhonesForRecord = validatePhones(record.getTelephones(), location, info.getStats(), commonData, phonesSet);
				String validMobilePhonesForRecord = validateMobilePhone(record.getMobile(), location, info.getStats(), commonData, phonesSet);
				if (!validPhonesForRecord.isEmpty() ||
						validMobilePhonesForRecord != null) {
					validRecords.add(new Record(location
												,validMobilePhonesForRecord 
												,validPhonesForRecord
												,record.getHeader()
												,record.getRowValues()
												,record.getTypeOfService()
												,record.getSuscriber()
												,record.getEmail()
												,record.getCampo1()
												,record.getCampo2()
												,record.getCampo3()
												,record.getCampo4()
												,record.getCampo5()
												,record.getCampo6()
												,record.getCampo7()
												,record.getCampo8()
												,record.getCampo9()
												,record.getCampo10()
												));
				}
			} else {
				info.getStats().incrementInvalidLocationsCount();
//				System.out.println("Location " + record.getLocation() + " is not valid");
				logger.error(new File(filepath).getName() + ": Location {} invalida", record.getLocation());
			}
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("{} locations fueron invalidas.", info.getStats().getInvalidLocationsCount());
			logger.info("{} telefonos duplicados fueron encontrados.", info.getStats().getDuplicatedPhonesCount());
			logger.info("{} telefonos fueron corregidos.", info.getStats().getCorrectedPhonesCount());
			logger.info("{} telefonos fueron invalidos y no pudieron ser corregidos.", info.getStats().getInvalidPhonesCount());
		}

		return validRecords;
	}
	
}
