package com.abc.ceop.dao.jpa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.dao.GenericDao;
import com.abc.ceop.dao.LoadLocationsDAO;
import com.abc.ceop.model.entities.CellPhonePattern;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.util.DataLoader;

@Repository
public class LoadLocationsJpaDao implements LoadLocationsDAO {

	@PersistenceContext(unitName = "pu")
	private EntityManager em;

	private void loadLocations(ApplicationContext context, String file, int countryPos, int statePos, int smallPos, int largePos, int prefixPos) {
		List<Location> locations = new ArrayList<Location>();
		
		GenericDao genericDao = context.getBean(GenericDao.class);
		LocationService locationService = context.getBean(LocationService.class);
		
		
		Country argentina = genericDao.getById(Country.class, 1L);
		Country chile = genericDao.getById(Country.class, 2L);
		Country ecuador = genericDao.getById(Country.class, 3L);
		Country venezuela = genericDao.getById(Country.class, 4L);
		Country colombia = genericDao.getById(Country.class, 5L);
		Country peru = genericDao.getById(Country.class, 6L);
		Country puertoRico = genericDao.getById(Country.class, 7L);
		Country uruguay = genericDao.getById(Country.class, 8L);

		Country trinidad = genericDao.getById(Country.class, 9L);
		Country barbados = genericDao.getById(Country.class, 10L);
		Country suriname = genericDao.getById(Country.class, 11L);
		Country curacao = genericDao.getById(Country.class, 12L);
		Country prudential = genericDao.getById(Country.class, 13L);

		int countDuplicates = 0;
		int countInserted = 0;
		int totalRows = 0;
		
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(file);
			
			System.out.println("Cargando archivo: " + file);
			
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			totalRows = sheet.getLastRowNum();
			
			for (int startRow = 1; startRow <= sheet.getLastRowNum(); startRow++) {
				Row row = sheet.getRow(startRow);
				
				String country = "";
				String state = "";
				String smallCity = "";
				String largeCity = "";
				Integer prefix = 0;

				if(row.getCell(countryPos) != null) {
					country = row.getCell(countryPos).getStringCellValue();
					country = StringUtils.isNotBlank(country)? country.toUpperCase().trim():null;
				}
				if(row.getCell(statePos) != null) {
					state = row.getCell(statePos).getStringCellValue();
					state = StringUtils.isNotBlank(state)? state.toUpperCase().trim():null;
				}
				if(row.getCell(smallPos) != null) {
					smallCity = row.getCell(smallPos).getStringCellValue();
					smallCity = StringUtils.isNotBlank(smallCity)? smallCity.toUpperCase().trim():null;
				}
				if(row.getCell(largePos) != null) {
					largeCity = row.getCell(largePos).getStringCellValue();
					largeCity = StringUtils.isNotBlank(largeCity)? largeCity.toUpperCase().trim():null;
				}
				if(row.getCell(prefixPos) != null ) {
					prefix = (int) row.getCell(prefixPos).getNumericCellValue();
				}		
				
				Country aux = null;
				String fixedPhoneDigitsCount = "";
				String cellPhoneDigitsCount = "";
				if("CHILE".equals(country)) {
					aux = chile;
					fixedPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());
					cellPhoneDigitsCount = String.valueOf(9 - prefix.toString().length());
				} else if("ECUADOR".equals(country)) {
					aux = ecuador;
					fixedPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());
					cellPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());
				} else if("VENEZUELA".equals(country)) {
					aux = venezuela;
					fixedPhoneDigitsCount = String.valueOf(10 - prefix.toString().length());
					cellPhoneDigitsCount = String.valueOf(10 - prefix.toString().length());
				} else if("COLOMBIA".equals(country)) {
					aux = colombia;
					fixedPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());
					cellPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());
				} else if("PERU".equals(country)) {
					aux = peru;
					fixedPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());
					cellPhoneDigitsCount = String.valueOf(9 - prefix.toString().length());
				} else if("PUERTO RICO".equals(country)) {
					aux = puertoRico;
					fixedPhoneDigitsCount = "7"; //String.valueOf(10 - prefix.toString().length());
					cellPhoneDigitsCount = "7"; //String.valueOf(10 - prefix.toString().length());
				} else if("URUGUAY".equals(country)) {
					aux = uruguay;
					fixedPhoneDigitsCount = String.valueOf(8 - prefix.toString().length());//"7";
					cellPhoneDigitsCount = "7";
					
				} else if("TRINIDAD & TOBAGO".equals(country)) {
					aux = trinidad;
					fixedPhoneDigitsCount = "7";
					cellPhoneDigitsCount = "7";
				} else if("BARBADOS".equals(country)) {
					aux = barbados;
					fixedPhoneDigitsCount = "7";
					cellPhoneDigitsCount = "7";
				} else if("SURINAME".equals(country)) {
					aux = suriname;
					fixedPhoneDigitsCount =  "6";
					cellPhoneDigitsCount =  "6";
				} else if("CURACAO".equals(country)) {
					aux = curacao;
					fixedPhoneDigitsCount = "8";
					cellPhoneDigitsCount = "8";
				} else if("PRUDENTIAL".equals(country)) {
                    aux = prudential;
                    fixedPhoneDigitsCount = String.valueOf(10 - prefix.toString().length());
                    cellPhoneDigitsCount = String.valueOf(10 - prefix.toString().length());
					
					
				} else if("ARGENTINA".equals(country)) {
					aux = argentina;
					String phoneDigitsCount = String.valueOf(10 - prefix.toString().length());
					fixedPhoneDigitsCount = phoneDigitsCount;
					cellPhoneDigitsCount = phoneDigitsCount;
				} else {
					continue;
				}
				
				//Mapa por defecto todo en true, no lee la tabla LocationSearchConfig donde seteamos como buscar una locacion 
			      Map <String, Boolean> map = DataLoader.generateSearchLocationMap();
				
				Location location = new Location(smallCity, largeCity, state, aux, String.valueOf(prefix), fixedPhoneDigitsCount, cellPhoneDigitsCount);
				Location locationDB = locationService.lookupLocationLike(location, map);
				if (locationDB != null &&
						DataLoader.locationEquals(locationDB,location)) {
					countDuplicates++;
					continue;
				}
				if (DataLoader.validateDuplicates(locations,location)) {
					continue;
				}
				locations.add(location);
			}
			
			int total = locations.size();
			System.out.println("Total procesados: " + locations.size());
			locations = DataLoader.removeDuplicates(locations);
			System.out.println("Total duplicados removidos: " + (total - locations.size()));
			countDuplicates = countDuplicates + (total - locations.size());
			
			for (Location location : locations) {
				
				em.persist(location);
				
				List<CellPhonePattern> list = new ArrayList<CellPhonePattern>();
				if("CHILE".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("9", "*9xxxxxxxx", location));
				} else if("ECUADOR".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("9", "*9xxxxxxx", location));
				} else if("VENEZUELA".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("416", "*416xxxxxxx", location));
					list.add(new CellPhonePattern("426", "*426xxxxxxx", location));
					list.add(new CellPhonePattern("414", "*414xxxxxxx", location));
					list.add(new CellPhonePattern("424", "*424xxxxxxx", location));
					list.add(new CellPhonePattern("412", "*412xxxxxxx", location));
				} else if("COLOMBIA".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("300", "*300xxxxxxx", location));
					list.add(new CellPhonePattern("301", "*301xxxxxxx", location));
					list.add(new CellPhonePattern("304", "*304xxxxxxx", location));
					list.add(new CellPhonePattern("310", "*310xxxxxxx", location));
					list.add(new CellPhonePattern("311", "*311xxxxxxx", location));
					list.add(new CellPhonePattern("312", "*312xxxxxxx", location));
					list.add(new CellPhonePattern("313", "*313xxxxxxx", location));
					list.add(new CellPhonePattern("314", "*314xxxxxxx", location));
					list.add(new CellPhonePattern("315", "*315xxxxxxx", location));
					list.add(new CellPhonePattern("316", "*316xxxxxxx", location));
					list.add(new CellPhonePattern("317", "*317xxxxxxx", location));
					list.add(new CellPhonePattern("318", "*318xxxxxxx", location));
					list.add(new CellPhonePattern("320", "*320xxxxxxx", location));
				} else if("PERU".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("9", "*9xxxxxxxx", location));
				} else if("PUERTO RICO".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("939", "*939xxxxxxx", location));
				} else if("URUGUAY".equals(location.getCountry().getName())) {
					list.add(new CellPhonePattern("9", "*9xxxxxxx", location));
				} else if("ARGENTINA".equals(location.getCountry().getName())) {
					String cellPattern = "*15" + StringUtils.repeat("x", 10 - location.getNationalCode().toString().length());
					CellPhonePattern cellPhonePattern = new CellPhonePattern("9", cellPattern, location);
					list.add(cellPhonePattern);

					cellPattern = "*9" + StringUtils.repeat("x", 10);
					cellPhonePattern = new CellPhonePattern("9", cellPattern, location);
					list.add(cellPhonePattern);
				} else if("SURINAME".equals(location.getCountry().getName())) {
				    list.add(new CellPhonePattern("8", "*8xxxxxx", location));
				    list.add(new CellPhonePattern("7", "*7xxxxxx", location));
				    list.add(new CellPhonePattern("6", "*6xxxxxx", location));
                  

            }
			    
				
				for (CellPhonePattern cellPhonePattern : list) {
					em.persist(cellPhonePattern);
				}
				countInserted++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Archivo " + file +  " no fue encontrado.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		
		System.out.println("Locaciones totales: " + totalRows);
		System.out.println("Locaciones Duplicadas: " + countDuplicates);
		System.out.println("Locaciones Insertadas: " + countInserted);
			
	}

	@Transactional(readOnly=false)
	@Override
	public void loadLocation(ApplicationContext context, String file) {
		loadLocations(context, file, 0, 1, 2, 3, 4);
	}
	

	@Transactional(readOnly=false)
	@Override
	public  void deleteLocationTables() {
//		EntityTransaction et = em.getTransaction();
		try {
//			et.begin();
			Query lquery = em.createNativeQuery("delete from cellphonepattern");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from location");
			lquery.executeUpdate();
			lquery = em.createNativeQuery("delete from country");
			lquery.executeUpdate();
//			et.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
//			et.rollback();
		}
	}

}
