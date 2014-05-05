package com.abc.ceop.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.dao.CheckDatabaseDao;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.phonepoll.service.impl.DialRecordCsvFileCreatorImpl;
/**
 * 
 * @author PVidela
 *
 */
public final class ReadLocationsFromDBMain {

    private static final String PATH = "databases/";
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DialRecordCsvFileCreatorImpl.class);
    public static int locacionesTotales = 0;

    private ReadLocationsFromDBMain() {} 

    public static void main(String[] args) {
      final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");

       final CheckDatabaseDao checkDatabaseDao = (CheckDatabaseDao) context.getBean("checkDatabaseDao");

       final File folder = new File(PATH);
      
       final List<Location> allLocationsNotFound = new ArrayList<Location>();
     
       
        if (!folder.exists()) {
            folder.mkdir();
        }
        
       final File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null || listOfFiles.length <= 0) {
            System.out.println("No hay archivos en el directorio databases");
           // LOGGER.info("No hay archivos en el directorio databases");
        } else {
            System.out.println("Hay " + listOfFiles.length + " archivos en el directorio databases");
           // LOGGER.info("Hay " + listOfFiles.length + " archivos en el directorio databases");
        }
      
        for (File file : listOfFiles) {

            if (!file.isFile()) {
                continue;
            }
            System.out.println("Procesando archivo " + file + " ...");
            //LOGGER.info("Procesando archivo " + file + " ...");
            checkDatabaseDao.checkPath(context, PATH, file, allLocationsNotFound, locacionesTotales );
        }
        
        System.out.println("Locaciones total:" + locacionesTotales);
        System.out.println("Locaciones encontradas:" + (locacionesTotales - allLocationsNotFound.size()));
        System.out.println("Locaciones no encontradas en base:" + allLocationsNotFound.size());
      
        checkDatabaseDao.createXlsWithAllLocations(allLocationsNotFound);

    }

    public static int getLocacionesTotales() {
        return locacionesTotales;
    }

    public static void setLocacionesTotales(int locacionesTotales) {
        ReadLocationsFromDBMain.locacionesTotales = locacionesTotales;
    }
}
