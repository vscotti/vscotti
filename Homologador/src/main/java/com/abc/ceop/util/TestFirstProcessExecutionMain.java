package com.abc.ceop.util;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.phoneapprover.service.FirstProcess;

public class TestFirstProcessExecutionMain {

	private TestFirstProcessExecutionMain() {}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");

		FirstProcess mainExecutor = context.getBean(FirstProcess.class);
		List<String> files = mainExecutor.executeCorrection("src/test/resources/ARG20110801IBS10.txt");
		if(files != null) {
			for (String file : files) {
				System.out.println(file);
			}
		}
	}
	
}
