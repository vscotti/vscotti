package com.abc.ceop.dao;

import java.io.File;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.abc.ceop.model.entities.Location;

public interface CheckDatabaseDao {

   void checkPath( ApplicationContext context, String path, File file, List<Location> allLocationsNotFound, int locacionesTotales);
   
   void createXlsWithAllLocations(List<Location> allLocationsNotFound);
  
}
