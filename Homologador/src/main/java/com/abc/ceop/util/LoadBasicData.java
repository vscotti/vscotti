package com.abc.ceop.util;

import javax.persistence.EntityManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class LoadBasicData {

	private LoadBasicData() {}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
		LocalContainerEntityManagerFactoryBean emf = context.getBean(LocalContainerEntityManagerFactoryBean.class);
		
		EntityManager em = emf.getObject().createEntityManager();
		
		DataLoader.deleteTables(em);
		DataLoader.loadCampaigns(em);
		DataLoader.loadCsvFileConfigurations(em);
		DataLoader.loadEmails(em);
		DataLoader.loadQuestions(em);
		DataLoader.loadThresholds(context, em);
	}
	
}
