package com.abc.ceop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.dao.CampaignDao;
import com.abc.ceop.dao.DataLoaderDao;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.model.entities.EmailContact;
import com.abc.ceop.model.entities.FirstProcessConfigurationVariable;
import com.abc.ceop.model.entities.LocationSearchConfig;
import com.abc.ceop.model.entities.PollQuestionMatcher;
import com.abc.ceop.model.entities.Questionnaire;
import com.abc.ceop.model.entities.SecondProcessConfigurationVariable;
import com.abc.ceop.model.entities.Thresholds;

@Repository
public class DataLoaderJpaDao implements DataLoaderDao {

	
	@PersistenceContext(unitName = "pu")
	private EntityManager em;

	@Transactional(readOnly = false)
	@Override
	public void deleteTables() {
		try {
			Query lquery = em.createNativeQuery("delete from emailcontact");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from emaillogs");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from csvfileconfiguration");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from questionnaire");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from PollQuestionMatcher");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from Thresholds");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from campaign");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from locationsearchconfig");
			lquery.executeUpdate();

		} catch (Exception exception) {
			exception.printStackTrace();

		}
	}

	@Override
	@Transactional(readOnly = false)
	public void loadCampaigns() {

		try {

			List<Campaign> list = new ArrayList<Campaign>();

			
			list.add(new Campaign("ARG", "906", 1L, "2368", 0, 0, ""));
			list.add(new Campaign("CHI", "912", 2L, "2367", 0, 0, ""));
			list.add(new Campaign("ECU", "908", 3L, "2366", 0, 0, ""));
			list.add(new Campaign("VEN", "913", 4L, "2365", 0, 0, ""));
			list.add(new Campaign("COL", "911", 5L, "2364", 0, 0, ""));
			list.add(new Campaign("PER", "909", 6L, "2363", 0, 0, ""));
			list.add(new Campaign("PUE", "910", 7L, "2362", 0, 0, ""));
			list.add(new Campaign("URU", "907", 8L, "2361", 0, 0, ""));

			list.add(new Campaign("TRI", "920", 9L, "2360", 0, 0, ""));
			list.add(new Campaign("BAR", "921", 10L, "2369", 0, 0, ""));
			list.add(new Campaign("SUR", "922", 11L, "2370", 0, 0, ""));
			list.add(new Campaign("CUR", "923", 12L, "2371", 0, 0, ""));

			for (Campaign campaign : list) {
				em.persist(campaign);
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		}
	}

	@Override
	@Transactional(readOnly = false)
	public void loadCsvFileConfigurations() {
		try {
			List<CsvFileConfiguration> list = new ArrayList<CsvFileConfiguration>();
			list.add(new CsvFileConfiguration("ARG", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 28));
			list.add(new CsvFileConfiguration("COL", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 27));
			list.add(new CsvFileConfiguration("PER", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 28));
			list.add(new CsvFileConfiguration("URU", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 28));
			list.add(new CsvFileConfiguration("PUE", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 28));
			list.add(new CsvFileConfiguration("CHI", 21, 23, 22, 24,
					"14,15,16", 17, 9, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 29));
			list.add(new CsvFileConfiguration("VEN", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 28));
			list.add(new CsvFileConfiguration("ECU", 19, 20, 21, 22,
					"12,13,14", 15, 7, 4, null, null, null, null, null, null,
					null, null, null, null, null, "YYYYMMDD", 28));
			list.add(new CsvFileConfiguration("CAR", 20, 21, 22, 23,
					"12,13,14", 16, 8, 5, null, null, null, null, null, null,
					null, null, null, null, null, "MMDDYYYY", 28));

			for (CsvFileConfiguration csvFileConfiguration : list) {
				em.persist(csvFileConfiguration);
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		}
	}

	@Override
	@Transactional(readOnly = false)
	public void loadEmails() {
		try {
			List<EmailContact> list = new ArrayList<EmailContact>();
			list.add(new EmailContact("Fabrizio", "Vazquez",
					"vazquezf@ceop.com.ar", "ERRORALERTS", null));
			list.add(new EmailContact("Fabrizio", "Vazquez",
					"vazquezf@ceop.com.ar", "UMBRALESALERT", null));
			for (EmailContact emailContact : list) {
				em.persist(emailContact);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void loadQuestions() {

		try {
			PollQuestionMatcher p1 = new PollQuestionMatcher((long) 1, "p1");
			PollQuestionMatcher p2 = new PollQuestionMatcher((long) 2, "p2");
			PollQuestionMatcher p3 = new PollQuestionMatcher((long) 3, "p3");
			PollQuestionMatcher p4 = new PollQuestionMatcher((long) 4, "p4");
			PollQuestionMatcher p5 = new PollQuestionMatcher((long) 5, "p5");
			PollQuestionMatcher p6 = new PollQuestionMatcher((long) 6, "p6");
			PollQuestionMatcher p7 = new PollQuestionMatcher((long) 7, "p7");
			PollQuestionMatcher p8 = new PollQuestionMatcher((long) 8, "p8");
			PollQuestionMatcher p9 = new PollQuestionMatcher((long) 9, "p9");

			em.persist(p1);
			em.persist(p2);
			em.persist(p3);
			em.persist(p4);
			em.persist(p5);
			em.persist(p6);
			em.persist(p7);
			em.persist(p8);
			em.persist(p9);

			for (int i = 10; i <= 20; i++) {
				em.persist(new PollQuestionMatcher((long) i, "p" + i));
			}
			em.persist(new PollQuestionMatcher(500L, "edad"));
			em.persist(new PollQuestionMatcher(510L, "edu"));
			em.persist(new PollQuestionMatcher(520L, "sexo"));
			em.persist(new PollQuestionMatcher(9000L, "year"));
			em.persist(new PollQuestionMatcher(9010L, "month"));
			em.persist(new PollQuestionMatcher(9020L, "day"));

			List<String> list = new ArrayList<String>();
			list.add("906");
			list.add("907");
			list.add("908");
			list.add("909");
			list.add("910");
			list.add("911");
			list.add("912");
			list.add("913");

			for (String campaignNumber : list) {
				em.persist(new Questionnaire(campaignNumber, "SQA", p1, 0));
				em.persist(new Questionnaire(campaignNumber, "SQB", p2, 0));
				em.persist(new Questionnaire(campaignNumber, "SQC", p3, 0));
				em.persist(new Questionnaire(campaignNumber, "Q1", p4, 0));
				em.persist(new Questionnaire(campaignNumber, "Q2", p5, 0));
				em.persist(new Questionnaire(campaignNumber, "Q3", p6, 0));
				em.persist(new Questionnaire(campaignNumber, "Q4", p7, 0));
				em.persist(new Questionnaire(campaignNumber, "Q5", p8, 0));
				em.persist(new Questionnaire(campaignNumber, "Q6", p9, 0));
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		}
	}

	@Override
	@Transactional(readOnly = false)
	public void loadThresholds(ApplicationContext context) {
	
		try {

			CampaignDao campaignDao = context.getBean(CampaignDao.class);
			List<Campaign> list = campaignDao.getAll();
	
			em.persist(new Thresholds("CUTTINGCONDITION", "", "P2 == 2 || P9 HASVALUE ", null));
			
			for (Campaign campaign : list) {
				em.persist(new Thresholds("FLAGEFECTIVO", "", "Q6 >= 1 && Q6 <= 2", campaign));
				em.persist(new Thresholds("FLAGSOS", "", "(Q1 == 1 || Q1 == 2) || (Q2A == 1 || Q2A == 2) || Q5A == 2", campaign));
			}
			for (Campaign campaign : list) {
				em.persist(new Thresholds("MINPHONECOUNT", "", "500", campaign));
				em.persist(new Thresholds("TIMEWITHOUTDATA", "", "24", campaign));
			}
			
		
		} catch (Exception exception) {
			exception.printStackTrace();
		
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void loadLocationSearchConfig() {
	
		try{
			List<LocationSearchConfig> list = new ArrayList<LocationSearchConfig>();
			
			list.add(new LocationSearchConfig("ARG", true, true, true, true));
			list.add(new LocationSearchConfig("CHI", true, true, true, true));
			list.add(new LocationSearchConfig("ECU", true, true, true, true));
			list.add(new LocationSearchConfig("VEN", true, true, true, true));
			list.add(new LocationSearchConfig("COL", true, true, true, true));
			list.add(new LocationSearchConfig("PER", true, true, true, true));
			list.add(new LocationSearchConfig("PUE", true, true, true, true));
			list.add(new LocationSearchConfig("URU", true, true, true, true));
			list.add(new LocationSearchConfig("TRI", true, true, true, true));
			list.add(new LocationSearchConfig("BAR", true, true, true, true));
			list.add(new LocationSearchConfig("SUR", true, true, true, true));
			list.add(new LocationSearchConfig("CUR", true, true, true, true));
			
			for (LocationSearchConfig load : list) {
				em.persist(load);
			}
		
			
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}		
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteConfigTables() {

			try {
			
				Query lquery = em.createNativeQuery("delete from firstprocessconfigurationvariable");
				lquery.executeUpdate();
				lquery = em.createNativeQuery("delete from secondprocessconfigurationvariable");
				lquery.executeUpdate();
			
			} catch (Exception exception) {
				exception.printStackTrace();
			
			}
		}

	@Override
	@Transactional(readOnly = false)
	public void loadFirstProcessConfiguration() {

			try {
				List<FirstProcessConfigurationVariable> list = new ArrayList<FirstProcessConfigurationVariable>();
				
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.INPUT_FTP_HOST, "190.210.93.12", "FTP donde lee el proceso 1")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.INPUT_FTP_USERNAME, "ivr-dtv", "Username FTP lectura proceso 1")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.INPUT_FTP_PASSWORD, "ceop5566", "Password FTP lectura proceso 1")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.INPUT_FTP_PATH, null, "Directorio donde se van a leer los archivos (opcional)")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.OUTPUT_FTP_HOST, "190.55.251.155", "FTP donde sube el proceso 1")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.OUTPUT_FTP_USERNAME, "neoclient", "Username FTP subida proceso 1"));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.OUTPUT_FTP_PASSWORD, "888888", "Password FTP subida proceso 1")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.OUTPUT_FTP_PATH, "ftp_subida", "Directorio donde se van a subir los archivos (opcional)")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.LOCAL_PREFIX, "", "Prefijo telefonico de salida nacional")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.INTERNATIONAL_PREFIX, "00", "Prefijo telefonico de salida internacional"));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.TEMP_PATH, "/temp", "Directorio temporal donde se guardan los archivos procesados")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.SMTP_HOST, "smtp.gmail.com", ""));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.SMTP_PORT, "587", ""));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.SMTP_AUTH, "true", ""));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.SMTP_TLS, "true", ""));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.EMAIL_USER, "ceoptest@gmail.com", ""));  
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.EMAIL_PASSWORD, "ceopceop", "")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.VALID_CAMPAIGN, "ARG", "Un Valid Campaign por cada una")); 
				list.add(new FirstProcessConfigurationVariable(FirstProcessConfiguration.DIALED_DIRECT, "FALSE", "Activar el marcado directo unicamente en Argentina")); 

				for (FirstProcessConfigurationVariable firstProcessConfigurationVariable : list) {
					em.persist(firstProcessConfigurationVariable);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	

	@Override
	@Transactional(readOnly = false)
	public void loadSecondProcessConfiguration() {
		
		try {
		
			List<SecondProcessConfigurationVariable> list = new ArrayList<SecondProcessConfigurationVariable>();
			
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.INPUT_FTP_HOST, "190.55.251.155", "FTP donde lee el proceso 2")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.INPUT_FTP_USERNAME, "neoclient", "Username FTP lectura proceso 2")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.INPUT_FTP_PASSWORD, "888888", "Password FTP lectura proceso 2")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.INPUT_FTP_PATH, "ftp_bajada", "Directorio donde se van a leer los archivos (opcional)")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.OUTPUT_FTP_HOST, "190.210.93.12", "FTP donde sube el proceso 2")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.OUTPUT_FTP_USERNAME, "ivr-dtv-bajada", "Username FTP subida proceso 2")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.OUTPUT_FTP_PASSWORD, "ceop5566", "Password FTP subida proceso 2")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.OUTPUT_FTP_PATH, null, "Directorio donde se van a subir los archivos (opcional)")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.TEMP_PATH, "/temp", "Directorio temporal donde se guardan los archivos procesados")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.PATH_MAIL_SEND, "/ruta", "Directorio donde se guardan el email de encuesta via web")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.SUBJECT_MAIL, "/temp", "String para Darle el subject al mail de encuesta web")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.DAYS_QRLE, "3", "cantidad de dias para buscar encuestas completadas")); 
			list.add(new SecondProcessConfigurationVariable(SecondProcessConfiguration.DAYS_EMAIL_SUS_WEB, "3", "cantidad de dias para buscar en emailSuscriberWeb")); 

			
			for (SecondProcessConfigurationVariable secondProcessConfigurationVariable : list) {
				em.persist(secondProcessConfigurationVariable);
			}
	
		} catch (Exception exception) {
			exception.printStackTrace();
		
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteLocationTables() {
		
			try {
				Query lquery = em.createNativeQuery("delete from cellphonepattern");
				lquery.executeUpdate();
				lquery = em.createNativeQuery("delete from location");
				lquery.executeUpdate();
				lquery = em.createNativeQuery("delete from country");
				lquery.executeUpdate();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	@Override
	@Transactional(readOnly = false)
	public void loadCountries() {


		try {
		
			Country argentina = new Country(1L, "ARGENTINA", "54", "9", 10L, 10L);
			Country chile = new Country(2L, "CHILE", "56", "9", 8L, 9L);
			Country ecuador = new Country(3L, "ECUADOR", "593", "9", 8L, 8L);
			Country venezuela = new Country(4L, "VENEZUELA", "58", "", 10L, 10L);
			Country colombia = new Country(5L, "COLOMBIA", "57", "", 8L, 10L);
			Country peru = new Country(6L, "PERU", "51", "9", 8L, 9L);
			Country puertoRico = new Country(7L, "PUERTO RICO", "1", "", 10L, 10L);
			Country uruguay = new Country(8L, "URUGUAY", "598", "9", 8L, 8L);
			
			Country trinidad = new Country(9L, "TRINIDAD & TOBAGO", "1", "", 7L, 7L);
			Country barbados = new Country(10L, "BARBADOS", "1", "", 7L, 7L);
			Country suriname = new Country(11L, "SURINAME", "", "", 7L, 7L);
			Country curacao = new Country(12L, "CURACAO", "", "", 8L, 8L);
	
			em.persist(argentina);
			em.persist(chile);
			em.persist(ecuador);
			em.persist(venezuela);
			em.persist(colombia);
			em.persist(peru);
			em.persist(puertoRico);
			em.persist(uruguay);
			em.persist(trinidad);
			em.persist(barbados);
			em.persist(suriname);
			em.persist(curacao);
			
		
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
	
	
		
	}
		
	
	


