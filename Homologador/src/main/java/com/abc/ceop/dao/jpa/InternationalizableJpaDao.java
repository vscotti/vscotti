package com.abc.ceop.dao.jpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.abc.ceop.dao.InternationalizableDao;

/**
 * 
 * @author LContigiani
 *
 */


public class InternationalizableJpaDao implements InternationalizableDao {

    @Override
    public void moveToInternational(String dataFile, String xls1, String xls2) {

        InputStream inputStreamOrig = null;
        InputStream inputStreamDest = null;
        FileOutputStream outputStreamDest = null;
        
        try {
            inputStreamOrig = new FileInputStream(dataFile);
            inputStreamDest = new FileInputStream(xls1);
            
            HSSFWorkbook workbookOrig = new HSSFWorkbook(inputStreamOrig);
            HSSFWorkbook workbookDest = new HSSFWorkbook(inputStreamDest);
            
            HSSFSheet sheetOrig = workbookOrig.getSheetAt(0);
            HSSFSheet sheetDest = workbookDest.getSheetAt(0);
            
            int totalRowsOrig = sheetOrig.getLastRowNum();
            int totalRowsDest = sheetDest.getLastRowNum();
            
            for (int i = 1 ; i < totalRowsOrig ; i++) {
                Row rowOrig = sheetOrig.getRow(i);
                Row rowDest = sheetDest.createRow(++totalRowsDest);
                
                String country = rowOrig.getCell(0) != null? rowOrig.getCell(0).getStringCellValue() : null;
                String state = rowOrig.getCell(1) != null? rowOrig.getCell(1).getStringCellValue() : null;
                String smallcity= rowOrig.getCell(2) != null? rowOrig.getCell(2).getStringCellValue() : null;
                String largecity = rowOrig.getCell(3) != null? rowOrig.getCell(3).getStringCellValue() : null;
                Double prefix = rowOrig.getCell(4) != null? rowOrig.getCell(4).getNumericCellValue() : null;
                
                rowDest.createCell(0).setCellValue(country);
                rowDest.createCell(1).setCellValue(state);
                rowDest.createCell(2).setCellValue(smallcity);
                rowDest.createCell(3).setCellValue(largecity);
                rowDest.createCell(4).setCellValue(prefix);
            }
            
            try {
                if (inputStreamDest != null) {
                    inputStreamDest.close();
                }
            } catch (IOException e) {
            }
            outputStreamDest = new FileOutputStream(xls2);
            workbookDest.write(outputStreamDest);
            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (inputStreamDest != null) {
                    inputStreamDest.close();
                }
            } catch (IOException e1) {
            }
        } finally {
            try {
                if (inputStreamOrig != null) {
                    inputStreamOrig.close();
                }
            } catch (IOException e) {
            }
            try {
                if (outputStreamDest != null) {
                    outputStreamDest.close();
                }
            } catch (IOException e) {
            }
            
            File f = new File(xls1);
            f.delete();
            
            f = new File(xls2);
            f.renameTo(new File(xls1));

        }
        
    }

}
