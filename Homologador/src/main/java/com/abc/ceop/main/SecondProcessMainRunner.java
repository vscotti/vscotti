package com.abc.ceop.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.phonepoll.service.SecondProcess;

public class SecondProcessMainRunner {
	
	private SecondProcessMainRunner() {}
	
	public static void main(String[] args) {
		System.out.println("Starting CEOP Second Batch process... Version 2.3");
		SecondProcess mainExecutor = null;
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
			
			mainExecutor = context.getBean(SecondProcess.class);
		} catch (Exception exception) {
			System.out.println("ERROR: could not load Spring context");
			exception.printStackTrace();
		}
		if (mainExecutor != null) {
			mainExecutor.execute();
		}
		System.out.println("Done.");
	}

}
