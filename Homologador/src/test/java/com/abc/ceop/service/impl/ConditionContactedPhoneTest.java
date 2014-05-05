package com.abc.ceop.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.abc.ceop.dao.RecordDetailDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })


public class ConditionContactedPhoneTest {

	 
	@Test
	@Rollback public void testcheckContactedPhones (){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
		RecordDetailDao dao = context.getBean(RecordDetailDao.class);
		dao.saveRecordDetail("65553416", 1);
		
	}
			
}
