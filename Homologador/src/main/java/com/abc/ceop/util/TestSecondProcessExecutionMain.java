package com.abc.ceop.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.phonepoll.service.impl.SecondProcessImplementation;



public class TestSecondProcessExecutionMain {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext( "classpath:/spring/model-context.xml");
		SecondProcessImplementation mainExecutor = context.getBean(SecondProcessImplementation.class);
		String filename ="src/test/resources/COL20120313IBS16.txt";
		mainExecutor.executeProcess(filename);
	
	}

}
