package com.abc.ceop.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.phoneapprover.service.FirstProcess;

public class FirstProcessMainRunner {
	
    public final static String Revision = "$Rev$ :"; 
    
	private FirstProcessMainRunner() {}
	
	public static void main(String[] args) {
		System.out.println("Starting CEOP First Batch process...Version 2.3");

		FirstProcess mainExecutor = null;
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
			mainExecutor = context.getBean(FirstProcess.class);
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
