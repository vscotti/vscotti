package com.abc.ceop.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.model.dto.FirstProcessCommonData;
import com.abc.ceop.model.entities.Configuration;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.phoneapprover.service.FirstProcessConfigurationService;
import com.abc.ceop.phoneapprover.service.PhoneCorrectorService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/model-context.xml"})
public class PhoneCorrectorServiceImplTest {

	@Resource
	private LocationService locationService;
	@Resource
	private PhoneCorrectorService phoneCorrectorService;
	@Resource
	private FirstProcessConfigurationService configurationService;

	private Country argentina = new Country(1L, "ARGENTINA", "54", "9", 10L, 10L);
	private Country chile = new Country(2L, "CHILE", "56", "9", 10L, 10L);
	private Country colombia = new Country(3L, "COLOMBIA", "57", "", 10L, 10L);
	private Country ecuador = new Country(4L, "ECUADOR", "593", "9", 10L, 10L);
	private Country peru = new Country(5L, "PERU", "51", "9", 10L, 10L);
	private Country uruguay = new Country(6L, "URUGUAY", "598", "9", 10L, 10L);
	private Country puertoRico = new Country(7L, "PUERTO RICO", "1", "", 10L, 10L);
	private Country venezuela = new Country(8L, "VENEZUELA", "58", "", 10L, 10L);
	
	private Location capitalFederal = new Location("CAPITAL FEDERAL", null, "CAPITAL FEDERAL", argentina, "11", "8", "8");
	private Location cordoba = new Location("CORDOBA", null, "CORDOBA", argentina, "351", "7", "7");
	private Location rosario = new Location("ROSARIO", null, "SANTA FE", argentina, "341", "7", "7");
	private Location santiago = new Location("SANTIAGO", null, "III REGION", chile, "2", "7", "8");
	private Location bogota = new Location("BOGOTA", null, "CUNDINAMARCA", colombia, "1", "7", "7");
	private Location quito = new Location("QUITO", null, "PICHINCHA", ecuador, "2", "7", "7");
	private Location lima = new Location("LIMA", null, "LIMA", peru, "1", "7", "8");
	private Location montevideo = new Location("MONTEVIDEO", null, "MONTEVIDEO", uruguay, "2", "7", "7");
	private Location sanJuan = new Location("SAN JUAN", null, null, puertoRico, "", "10", "7");
	private Location caracas = new Location("DISTRITO FEDERAL", null, "CARACAS", venezuela, "212", "7", "7");
	
	//Mapa por defecto todo en true, no lee la tabla LocationSearchConfig donde seteamos como buscar una locacion 
	private static Map<String, Boolean> generateSearchLocationMap() {
		
		Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
		map.put("country", true);
		map.put("state", true);
		map.put("largeCity", true);
		map.put("smallCity", true);
		
		return map;
	}
	
	
	
	@Test
	public void test_corrected_Argentina() {
		 Map <String, Boolean> map = generateSearchLocationMap();
		Location loccapitalFederal = locationService.lookupLocationLike(capitalFederal, map);
		Location loccordoba = locationService.lookupLocationLike(cordoba, map);
		Location locrosario = locationService.lookupLocationLike(rosario, map);
		
		// capital federal fixed phones
		String expectedCorrection = "00541177777777";
		_test_corrected("77777777", expectedCorrection, loccapitalFederal, false);
		_test_corrected("005477777777", expectedCorrection, loccapitalFederal, false);
		
		// capital federal cell phones
		expectedCorrection = "005491177777777";
		_test_corrected("1577777777", expectedCorrection, loccapitalFederal, true);
		_test_corrected("541577777777", expectedCorrection, loccapitalFederal, true);
		_test_corrected("00541577777777", expectedCorrection, loccapitalFederal, true);
		
		// capital federal cell phones
		expectedCorrection = "005491177777777";
		_test_corrected("1177777777", expectedCorrection, loccapitalFederal, false);
		_test_corrected("541177777777", expectedCorrection, loccapitalFederal, false);
		_test_corrected("00541177777777", expectedCorrection, loccapitalFederal, false);
		
		expectedCorrection = "00543517777777";
		_test_corrected("7777777", expectedCorrection, loccordoba, false);
		_test_corrected("117777777", expectedCorrection, loccordoba, false);
		_test_corrected("00547777777", expectedCorrection, loccordoba, false);
		
		expectedCorrection = "00543417777777";
		_test_corrected("7777777", expectedCorrection, locrosario, false);
		_test_corrected("117777777", expectedCorrection, locrosario, false);
		_test_corrected("00547777777", expectedCorrection, locrosario, false);
	}
	
	@Test
	public void test_corrected_Chile() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(santiago, map);
		
		String expectedCorrection = "005627777777";
		_test_corrected("567777777", expectedCorrection, loc, false);
		
		expectedCorrection = "0056977777777";
		_test_corrected("977777777", expectedCorrection, loc, false);
	}
	
	@Test
	public void test_corrected_Colombia() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(bogota, map);
		
		String expectedCorrection = "00573127777777";
		_test_corrected("3127777777", expectedCorrection, loc, false);
		_test_corrected("00573127777777", expectedCorrection, loc, false);
	}
	
	@Test
	public void test_corrected_Ecuador() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(quito, map);
		
		String expectedCorrection = "0059397777777";
		_test_corrected("97777777", expectedCorrection, loc, false);		
	}
	
	@Test
	public void test_corrected_Peru() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(lima, map);
		
		String expectedCorrection = "005117777777";
		_test_corrected("7777777", expectedCorrection, loc, false);
		
		expectedCorrection = "0051977777777";
		_test_corrected("977777777", expectedCorrection, loc, false);				
	}
	
	@Test
	public void test_corrected_Uruguay() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(montevideo, map);
		
		String expectedCorrection = "0059894551654";
		_test_corrected("094551654", expectedCorrection, loc, false);
		
		expectedCorrection = "0059827777777";
		_test_corrected("5987777777", expectedCorrection, loc, false);
	}
	
	@Test
	public void test_corrected_PuertoRico() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(sanJuan, map);
		
		String expectedCorrection = "0017872430606";
		_test_corrected("7872430606", expectedCorrection, loc, false);
		
		expectedCorrection = "0019397777777";
		_test_corrected("9397777777", expectedCorrection, loc, false);
	}
	
	@Test
	public void test_corrected_Venezuela() {
		Map <String, Boolean> map = generateSearchLocationMap();
		Location loc = locationService.lookupLocationLike(caracas, map);
		
		String expectedCorrection = "00582127777777";
		_test_corrected("587777777", expectedCorrection, loc, false);
		
		expectedCorrection = "00584127777777";
		_test_corrected("4127777777", expectedCorrection, loc, false);
	}
	
	private void _test_corrected(String phoneToCorrect, String expectedCorrection, Location location, boolean isMobile) {
		if (!phoneCorrectorService.isCorrectable(phoneToCorrect, location)) {
			Assert.fail();
		}

		FirstProcessCommonData cd = new FirstProcessCommonData();
		cd.setMaxNationalCodeLength(locationService.getMaxNationalCodeLenght(location.getCountry()));
		cd.setLocalPrefix(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.LOCAL_PREFIX));
		cd.setInternationalPrefix(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INTERNATIONAL_PREFIX));
		cd.setLocationsForcountry(locationService.getAllForCountry(location.getCountry()));
		
		String correctedPhone = phoneCorrectorService.correct(phoneToCorrect, location, cd, isMobile);
		Assert.assertEquals(expectedCorrection, correctedPhone);
	}

	}
