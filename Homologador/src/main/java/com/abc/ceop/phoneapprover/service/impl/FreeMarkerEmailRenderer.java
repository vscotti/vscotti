package com.abc.ceop.phoneapprover.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.abc.ceop.model.dto.FirstProcessResultInfo;
import com.abc.ceop.model.dto.SecondProcessResultInfo;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.phoneapprover.service.EmailRenderer;
import com.abc.ceop.phonepoll.service.SecondProcessConfigurationService;

import freemarker.template.Configuration;
import freemarker.template.Template;



@Service
public class FreeMarkerEmailRenderer implements EmailRenderer {
	
	@Resource
	private  SecondProcessConfigurationService configurationService;

	@Override
	public String render(String title, FirstProcessResultInfo info, boolean isSecondEmail) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", title);
		if(!isSecondEmail) {
			model.put("correctedPhones", info.getStats().getCorrectedPhonesCount());
			model.put("duplicatedPhones", info.getStats().getDuplicatedPhonesCount());
			model.put("invalidLocations", info.getStats().getInvalidLocationsCount());
			model.put("invalidPhones", info.getStats().getInvalidPhonesCount());
			model.put("time", new Date());
			model.put("result", info.getResult());
			model.put("sosDetectec", -1);
			model.put("noDBDetected", -1);
			model.put("averaragePhones", -1);
			return renderWithFreeMarker(model);
		} else {
			model.put("correctedPhones", -1);
			model.put("duplicatedPhones", -1);
			model.put("invalidLocations", -1);
			model.put("invalidPhones", -1);
			model.put("time", new Date());
			model.put("result", "");
			model.put("sosDetectec", -1);
			model.put("noDBDetected", info.getNoDBDetected());
			model.put("averaragePhones", info.getAveraragePhones());
			model.put("campaignCountry", info.getCampaignCountry().getCountry());
			model.put("filename",  (info.getFileName()!= null )? new File(info.getFileName()).getName(): null);
			
			return renderWithFreeMarkerSecond(model);
		}
	}
	
	@Override
	public String render(String title, SecondProcessResultInfo info) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", title);
		model.put("correctedPhones", -1);
		model.put("duplicatedPhones", -1);
		model.put("invalidLocations", -1);
		model.put("invalidPhones", -1);
		model.put("time", new Date());
		model.put("result", "");
		model.put("sosDetectec", 1);
		model.put("noDBDetected", -1);
		model.put("averaragePhones", -1);
		return renderWithFreeMarker(model);
	}

	
	@Override
	public String render(String title, String encrypterSubscriber) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", title);
		model.put ("suscriber", encrypterSubscriber);
		model.put("time", new Date());
			
		return renderWithFreeMarkerWebSurveyMail(model);
	}
	
	private String renderWithFreeMarker(Map<String, Object> values) throws Exception {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("mail.ftl")));
		String str = FreeMarkerTemplateUtils.processTemplateIntoString(
				new Template("mail.ftl", isr,
						new Configuration()), values);
		return str;
	}

	private String renderWithFreeMarkerSecond(Map<String, Object> values) throws Exception {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("secondMail.ftl")));
		String str = FreeMarkerTemplateUtils.processTemplateIntoString(
				new Template("secondMail.ftl", isr,
						new Configuration()), values);
		return str;
	}
	
	private String renderWithFreeMarkerWebSurveyMail (Map<String, Object> values) throws Exception {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(configurationService.getConfigurationValue(SecondProcessConfiguration.PATH_MAIL_SEND))));
		String str = FreeMarkerTemplateUtils.processTemplateIntoString(
				new Template("surveyWeb.html", isr, new Configuration()), values);
		return str;
	}	
}
