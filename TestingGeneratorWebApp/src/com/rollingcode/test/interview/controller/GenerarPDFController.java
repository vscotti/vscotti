package com.rollingcode.test.interview.controller;

import org.springframework.stereotype.Controller;

@Controller
public class GenerarPDFController {
//
//
//	@RequestMapping("/generarPDF/{type}")
//	public void generarArchivo(@ModelAttribute ResultInfo resultInfo, 
//							   @PathVariable String type,
//							   HttpServletResponse response, 
//							   Model model) throws Exception {
//		Map<String, List<PreguntaBO>> preguntasSeleccionadas = new HashMap<String, List<PreguntaBO>>();
//		for (PreguntaVO pregunta : resultInfo.getPreguntas()) {
//			if((type != null &&
//					((type.startsWith("seleccion") && pregunta.isSeleccionado()) ||
//					type.startsWith("completo"))) ||
//					type == null) {
//				PreguntaBO pbo = new PreguntaBO(pregunta.getPregunta());
//				List<PreguntaBO> list = preguntasSeleccionadas.get(pbo.getCategoria());
//				if(list == null) {
//					list = new ArrayList<PreguntaBO>();
//				}
//				list.add(pbo);
//				preguntasSeleccionadas.put(pbo.getCategoria(), list);
//			}
//		}
//		String fileName = "SalesReport.pdf";   
//
//		PDFGenerator.generatePDF(fileName, type, preguntasSeleccionadas);
//
//		File file = new File(fileName);
//		FileInputStream archivoL = new FileInputStream(fileName);   
//		byte fileContent[] = new byte[(int)file.length()];
//		archivoL.read(fileContent);
//		archivoL.close();
//
//		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
//		response.setContentType("application/pdf");       
//		ServletOutputStream outputStream = response.getOutputStream();    
//		outputStream.write(fileContent);    
//		outputStream.flush();     
//	}
//	
}
