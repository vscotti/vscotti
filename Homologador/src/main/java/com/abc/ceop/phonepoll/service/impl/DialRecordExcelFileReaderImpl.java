package com.abc.ceop.phonepoll.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.common.service.RecordDetailService;
import com.abc.ceop.enums.CellField;
import com.abc.ceop.enums.CellValue;
import com.abc.ceop.enums.Messages;
import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.dto.DialedRecord;
import com.abc.ceop.model.entities.RecordDetail;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileReader;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;

@Service
public class DialRecordExcelFileReaderImpl implements DialRecordExcelFileReader {

	@Resource
	private PollQuestionMatcherService pollQuestionMatcherService;
	@Resource
	private RecordDetailService recordDetailService;
	@Resource
    private CampaignService campaignService;
	
	
	
	
	private final Logger logger = LoggerFactory
			.getLogger(DialRecordExcelFileReaderImpl.class);
		
		
	@Override
	public List<DialedRecord> readExcel(String filepath) {
	    
	    logger.info("executing readExcel -> filepath -> " + filepath);
	    
	    String condicion = null;
	    File file = new File(filepath);
	    String path = file.getAbsolutePath();
	   String nombre = new File(path).getName().split("\\s")[0];
	   String camp = nombre.substring(1, 4);
       
	   //Trae el valor de la tabla column log filter para setear el filtrar segun arbol
	   logger.info("Buscando en la base la condicion para filtrar por arbol");
	   condicion = campaignService.getCampaignByCampaignId(camp).getColumnLogFilter();
       if (condicion != null && !condicion.isEmpty()){
	          condicion = condicion.trim();
	          logger.info("La condicion Obtenida de la base fue : " + condicion);
	       }else {
            logger.info("La condicion esta vacia o en null, condicion = " + condicion);
        }
	   
   
		FileInputStream fileIn = null;
		BufferedInputStream bufferedInputStream = null;
		List<DialedRecord> dialedRecords = new ArrayList<DialedRecord>();
		
		try {
		    logger.info("Instanciando FileInputStream...");
		    
			fileIn = new FileInputStream(filepath);
			
			logger.info("FileInputStream listo..");
			
			bufferedInputStream = new BufferedInputStream(fileIn);
			HSSFWorkbook workbook = new HSSFWorkbook(bufferedInputStream);
			// linea que hace la salida por consola Warning, header block comes after data blocks in POIFS block listing
			HSSFSheet sheet = workbook.getSheetAt(0);
			int lastRow = sheet.getLastRowNum();
			Long startId = null;
			Long currentId = null;
			DialedRecord dialedRecord = null;
			String currentCellValue = null;
			String[] valuesMatched = null;
			boolean isEmpty = true;
			boolean hasValue = false;
						
					
			String valorDelArbol = null;
			Set<String> arbolAborrar = new HashSet<String>();
			
			for (int startRow = 1; startRow <= lastRow; startRow++) {
				Row row = sheet.getRow(startRow);
				Long idCaller = row.getCell(CellField.CALLER_ID_CELL.getValue()) != null? new Double(row.getCell(CellField.CALLER_ID_CELL.getValue()).getNumericCellValue()).longValue() : 0L;
				Integer node = (int) (row.getCell(CellField.NODE_CELL.getValue()) != null? row.getCell(CellField.NODE_CELL.getValue()).getNumericCellValue() : 0);
				if(row.getCell(CellField.ANI_TELEFONO.getValue()) != null) {
					row.getCell(CellField.ANI_TELEFONO.getValue()).setCellType(Cell.CELL_TYPE_STRING);
				}
				String phone = row.getCell(CellField.ANI_TELEFONO.getValue()) != null? row.getCell(CellField.ANI_TELEFONO.getValue()).getStringCellValue() : "";
				String type = row.getCell(CellField.READ_VAR_EVAL_COND.getValue()) != null? row.getCell(CellField.READ_VAR_EVAL_COND.getValue()).getStringCellValue() : "";
				String logValue = row.getCell(CellField.ANSWER_CELL.getValue()) != null? row.getCell(CellField.ANSWER_CELL.getValue()).getStringCellValue() : ""; //lee columna LOG del Xls
				String arbol = row.getCell(CellField.ARBOL_CELL.getValue()) != null ? String.valueOf(row.getCell(CellField.ARBOL_CELL.getValue()).getNumericCellValue()) : "";  //le columna Arbol del xls
				
				
				if(StringUtils.isNotBlank(condicion)){
				//setea el arbol que no necesita guardar en DialedRecord
                if (valorDelArbol == null) {
                    if (logValue.startsWith(condicion)) {
                        valorDelArbol = arbol;
                        arbolAborrar.add(arbol);
                        logger.info("el arbol seteado es :" + arbolAborrar);
                    }
                }
				}
				
				if(!arbol.equals(valorDelArbol)){
                            
                            
                        
				if (CellValue.READ_VAR.getValue().equals(type)) {
						currentCellValue = CellValue.READ_VAR.getValue();
						String data = "";
						if (row.getCell(CellField.DATA_CELL.getValue()) != null) {
						row.getCell(CellField.DATA_CELL.getValue()).setCellType(Cell.CELL_TYPE_STRING);
							data = row.getCell(CellField.DATA_CELL.getValue()).getStringCellValue();
							if ("".equals(data) || !data.matches("\\d+")) {
								data = "";
								hasValue = false;
							} else {
								hasValue = true;
								if (StringUtils.isNotBlank(data)) {
									valuesMatched = logValue.split("=");
								}
							}
						} else {
							hasValue = false;
						}
						currentId = idCaller;
						
				} else if (CellValue.EVAL_COND.getValue().equals(type) && hasValue && logValue.contains("True") && CellValue.READ_VAR.getValue().equals(currentCellValue)) {
					currentCellValue = CellValue.EVAL_COND.getValue(); 
					if (currentId.equals(startId)) {
						if(valuesMatched != null &&
								valuesMatched.length > 1) {
							Long nd = pollQuestionMatcherService.getCallIdByCode(valuesMatched[0]);
							if(nd != null) {
								node = nd.intValue();
							}
							DialedOption dialedOption = new DialedOption(node, valuesMatched[0]);
							dialedRecord.getDialedValues().put(dialedOption,  Integer.parseInt(valuesMatched[1]));
						}
						isEmpty = false;
					}
				} else if ("Start".equals(type)) {
					Date date = row.getCell(CellField.DATE_CELL.getValue()) != null? row.getCell(CellField.DATE_CELL.getValue()).getDateCellValue() : null;
					if (dialedRecord != null) {
					    dialedRecord.setArbol(arbol);
						dialedRecords.add(dialedRecord);
					}
					if (idCaller != startId) {
						dialedRecord = null;
					}
					if (dialedRecord == null) {
						dialedRecord = new DialedRecord();
						startId = idCaller;
						dialedRecord.setArbol(arbol);
						dialedRecord.setCallerId(startId);
						dialedRecord.setIdLlamada(phone);
						dialedRecord.setCallDate(date);
					} else if (!isEmpty) {
						dialedRecords.add(dialedRecord);
						dialedRecord = new DialedRecord();
						startId = idCaller;
						dialedRecord.setArbol(arbol);
						dialedRecord.setCallerId(startId);
						dialedRecord.setIdLlamada(phone);
						dialedRecord.setCallDate(date);
					}
				} 
				if (startRow == lastRow) { 
					dialedRecords.add(dialedRecord);
				} else if ("End".equals(type)) {
					dialedRecords.add(dialedRecord);
					dialedRecord = null;
				}
			}
			}
			//end for
			logger.info("Removiendo de la lista de Dialead Records el arbol a borrar :" + arbolAborrar);
			removeDialedRecordsWithWrongArbol(dialedRecords, arbolAborrar);
			logger.info("La Cantidad de DialedRecords son :" + dialedRecords.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Messages.EXCEL.getValue(), e.getClass().toString(), e.getMessage());
		} finally {
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
				if (fileIn != null) {
					fileIn.close();
				}
			} catch (IOException e) {
				logger.error(Messages.EXCEL.getValue(), e.getClass().toString(), e.getMessage());
			}
		}
		return removeDuplicated(removeDuplicatedSuscriber(dialedRecords));
	}

    private void removeDialedRecordsWithWrongArbol(List<DialedRecord> dialedRecords, Set<String> arbolAborrar) {
       List<DialedRecord> toRemove = new ArrayList<DialedRecord>();
        for (DialedRecord dialedR : dialedRecords) {
            if(dialedR != null) {
                for (String arbol : arbolAborrar) {
                    if (dialedR.getArbol().equals(arbol)) toRemove.add(dialedR);
                }
            }
        }
        dialedRecords.removeAll(toRemove);
        logger.info("fueron removidos " + toRemove.size() + " dialedRecord con arbol bilingue" );
    }

	private List<DialedRecord> removeDuplicatedSuscriber(List<DialedRecord> list) {
	    logger.info("Entrando a RemoveDuplicatedSuscriber");
		Map<String, String> records = new TreeMap<String, String>();
		logger.info("Iterando la lista de DialededRecords");
		for (DialedRecord dialedRecord : list) {
		    if (dialedRecord != null) {
		        logger.info("El Id llamada del primer For es : " + dialedRecord.getIdLlamada()  );
    			RecordDetail rd = recordDetailService.getRecordDetailByPhoneId(dialedRecord.getIdLlamada());
    			if(rd != null) {
    				records.put(dialedRecord.getIdLlamada(), rd.getSuscriberId());
    				// Nosotros no encontramos macheo entre la base y el archivo de entrada del segundo proceso (referido al logger siguiente)
    				logger.info("Fue encontrado en la base RecordDetail el suscriber : " + rd.getSuscriberId() + " Con el Id llamada: " + dialedRecord.getIdLlamada());
    			
    			}
		    }
		}
		logger.info("Se creo la lista de Records con : " + records.size() + " records");
		
		
		List<DialedRecord> aux = new ArrayList<DialedRecord>();
		for (DialedRecord dialedRecord : list) {
		    if (dialedRecord != null) {
		        logger.info("El Id llamada del segundo For es : " + dialedRecord.getIdLlamada()  );
    			String suscriber = records.get(dialedRecord.getIdLlamada());
    			if(suscriber != null) {
    			    logger.info("El suscriber encontrado fue : " );
    				Collection<String> keys = records.keySet();
    				DialedRecord dialedRecordMax = dialedRecord;
    				boolean hasValue = false;
    				for (String str : keys) {
    					if(records.get(str) != null &&
    							records.get(str).equals(suscriber)) {
    						hasValue = true;
    						DialedRecord dialedRecordAux = null;
    						for (DialedRecord drl : list) {
    						    
    						    if (drl != null) {
    						    
    						    //TODO: Comentar el siguiente if luego de la prueba de Fabrizio..
    						    logger.info("Linea 258");
    						    if (drl.getIdLlamada()==null){
    						        logger.info("!!! ID Llamada es NULL en:");
    						        logger.info("CallerId -> " + drl.getCallerId());
    						        logger.info("Arbol -> " + drl.getArbol());
    						        logger.info("Source -> " + drl.getSource());
    						        logger.info("Call Date -> " + drl.getCallDate());
    						    }
    						    logger.info("el id llamada que no fue null del tercer For : "  + drl.getIdLlamada());
    							if(drl.getIdLlamada()!=null && drl.getIdLlamada().equals(str)) {
    								if(drl.getCallerId() != dialedRecord.getCallerId()) {
    									dialedRecordAux = drl;
    									break;
    								}
    							}
    						    } else {
    						        logger.info("EL dialed Record llamado drl es NULL !!!!");
    						    }
    						    
    						}
    						if(dialedRecordAux != null) {
    							if(dialedRecordAux.getDialedValues().size() > dialedRecordMax.getDialedValues().size()) {
    								dialedRecordMax = null;
    								break;
    							}
    						}
    					}
    				}
    				if(!hasValue) {
    					aux.add(dialedRecord);
    				} else {
    					if(dialedRecordMax != null) {
    						aux.add(dialedRecordMax);
    					}
    				}
    			} else {
    				aux.add(dialedRecord);
    			}
		    }
		}
		return aux;
	}
	
	private List<DialedRecord> removeDuplicated(List<DialedRecord> list) {
		List<DialedRecord> aux = new ArrayList<DialedRecord>();
		for (DialedRecord dialedRecord : list) {
			boolean hasValue = false;
			for (DialedRecord dialedRecord2 : aux) {
				if(dialedRecord2.getIdLlamada().equals(dialedRecord.getIdLlamada())) {
					hasValue = true;
					break;
				}
			}
			if(!hasValue) {
				aux.add(dialedRecord);
			}
		}
		return aux;
	}
	
}
