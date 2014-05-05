package com.abc.ceop.dao.jpa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.abc.ceop.common.service.CsvFileConfigurationService;
import com.abc.ceop.common.service.LocationSearchConfigService;
import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.dao.CheckDatabaseDao;
import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.model.entities.LocationSearchConfig;
import com.abc.ceop.phoneapprover.service.PhonesCsvFileReader;
import com.abc.ceop.phonepoll.service.impl.DialRecordCsvFileCreatorImpl;
import com.abc.ceop.util.DataLoader;
import com.abc.ceop.util.ReadLocationsFromDBMain;

/**
 * 
 * @author PVidela
 * 
 */
public class CheckDatabaseJpaDao implements CheckDatabaseDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DialRecordCsvFileCreatorImpl.class);

    @Override
    public void checkPath(ApplicationContext context, String path, File file, List<Location> allLocationsNotFound, int locacionesTotales) {
        final int posSubString = 3;
        LocationService locationService = context.getBean(LocationService.class);
        CsvFileConfigurationService csvFileConfigurationService = context.getBean(CsvFileConfigurationService.class);
        PhonesCsvFileReader phonesCsvFileReader = context.getBean(PhonesCsvFileReader.class);
        LocationSearchConfigService locationSearchConfigService = context.getBean(LocationSearchConfigService.class);
//       int locacionesTotales = 0;
        List<Location> locationNotFound = new ArrayList<Location>();

        String filepath = path + file.getName();
        String countryName = getCountryNameFromFile(filepath);
        CsvFileConfiguration config = csvFileConfigurationService.getCsvFileConfiguration(countryName);

        List<Location> locations = new ArrayList<Location>();
        List<Record> unprocessedRecords = new ArrayList<Record>();
        unprocessedRecords.addAll(phonesCsvFileReader.readFile(filepath, config));

        int locacionesParciales = ReadLocationsFromDBMain.getLocacionesTotales() + unprocessedRecords.size();
        ReadLocationsFromDBMain.setLocacionesTotales(locacionesParciales);

        for (Record record : unprocessedRecords) {
            if (!DataLoader.validateDuplicates(locations, record.getLocation())) {
                locations.add(record.getLocation());
            }
        }

        if (countryName.equalsIgnoreCase("CAR")) {

            for (Location loc : locations) {

                String caribeCountryName = loc.getCountry().toString().substring(0, posSubString);
                LocationSearchConfig locationSearchConfig = locationSearchConfigService
                        .getLocationSearchConfig(caribeCountryName);
                Map<String, Boolean> searchLocationMap = generateSearchLocationMap(locationSearchConfig);

                Location location = locationService.lookupLocationLike(loc, searchLocationMap);
                if (location == null) {
                    locationNotFound.add(makeLocationByMap(loc, searchLocationMap));
                }
            }
            allLocationsNotFound.addAll(locationNotFound);

        } else {

            LocationSearchConfig locationSearchConfig = locationSearchConfigService
                    .getLocationSearchConfig(countryName);
            Map<String, Boolean> searchLocationMap = generateSearchLocationMap(locationSearchConfig);

            for (Location loc : locations) {
                Location location = locationService.lookupLocationLike(loc, searchLocationMap);
                if (location == null) {
                    locationNotFound.add(makeLocationByMap(loc, searchLocationMap));
                }
            }
            allLocationsNotFound.addAll(locationNotFound);
        }
              
//        LOGGER.info("Locaciones total:" + locacionesTotales);
//        LOGGER.info("Locaciones encontradas:" + (locacionesTotales - locationNotFound.size()));
//        LOGGER.info("Locaciones no encontradas en base:" + locationNotFound.size());
     
    }
   
    @Override
    public void createXlsWithAllLocations(List<Location> allLocationsNotFound) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Locaciones");

            int i = 0;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue("PAIS");
            row.createCell(1).setCellValue("ESTADO/PROVINCIA");
            row.createCell(2).setCellValue("SMALL CITY");
            row.createCell(3).setCellValue("LARGE CITY");

            for (Location location : allLocationsNotFound) {
                row = sheet.createRow(++i);
                row.createCell(0).setCellValue(location.getCountry() == null ? null : location.getCountry().getName());
                row.createCell(1).setCellValue(location.getState());
                row.createCell(2).setCellValue(location.getSmallCity());
                row.createCell(3).setCellValue(location.getLargeCity());
            }
            String dest = "resultados/invalidlocation-" + new Date().getTime() + ".xls";
            new File("resultados/").mkdir();
            workbook.write(new FileOutputStream(dest));


            System.out.println("Se genero el archivo: " + dest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
   

    private Location makeLocationByMap(final Location loc, final Map<String, Boolean> searchLocationMap) {

        Location locationBymap = new Location();

        if (searchLocationMap.get("country").equals(true)) {
            locationBymap.setCountry(loc.getCountry());
        }

        if (searchLocationMap.get("state").equals(true)) {
            locationBymap.setState(loc.getState());
        }

        if (searchLocationMap.get("largeCity").equals(true)) {
            locationBymap.setLargeCity(loc.getLargeCity());
        }

        if (searchLocationMap.get("smallCity").equals(true)) {
            locationBymap.setSmallCity(loc.getSmallCity());
        }

        return locationBymap;
    }

    private Map<String, Boolean> generateSearchLocationMap(LocationSearchConfig locationSearchConfig) {

        Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
        map.put("country", locationSearchConfig.isCountry());
        map.put("state", locationSearchConfig.isState());
        map.put("largeCity", locationSearchConfig.isLargeCity());
        map.put("smallCity", locationSearchConfig.isSmallCity());

        return map;
    }

    private String getCountryNameFromFile(String filepath) {

        return new File(filepath).getName().split("\\d")[0];
    }

}
