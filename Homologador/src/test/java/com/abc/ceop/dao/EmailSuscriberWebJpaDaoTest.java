package com.abc.ceop.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.ceop.model.entities.QuienRespondeLaEncuesta;

/**
 * @author LContigiani
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class EmailSuscriberWebJpaDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private EmailSuscriberWebDao emailSuscriberWebDao;
    @Resource
    private QuienRespondeLaEncuestaDao quienRespondeLaEncuestaDao;
   
    @Test
    public void test1() throws Exception {
        List<String> result = emailSuscriberWebDao.getCompletedSurvey(0, "PRU", 9, new Date());
        Assert.assertTrue(null != result && !result.isEmpty());
    }
    
    @Test
    public void test2() throws Exception {
        
        List <String> suscribers = new ArrayList<String>();
        suscribers.add("1341077842");
        List <QuienRespondeLaEncuesta> result = quienRespondeLaEncuestaDao.getSurveyCompleteNdays("2615", 9, suscribers);
        Assert.assertTrue(null != result && !result.isEmpty());
      }
}
