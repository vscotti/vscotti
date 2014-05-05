package com.uade.tesis.web;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class UploadController extends AbstractController {

	private final String PATH = "//home//msuarez//tesisTmp//images";
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/* STEP 1. 
		   Handle the incoming request from the client
		 */

		
		
		// Create a factory for disk-based file items  
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1000000000);

		// Parse the request into a list of DiskFileItems
		List<DiskFileItem> items = upload.parseRequest(request);

		// The fields we will need for the API request
		File imageFile = null;
		String imageFilename = "";
		long imageMaxSize = 0;

		// Iterate through the list of DiskFileItems
		Iterator<DiskFileItem> iter = items.iterator();  
		while (iter.hasNext()) {
			DiskFileItem item = (DiskFileItem) iter.next();

			if (!item.isFormField()) {
				imageFile = item.getStoreLocation();
				String fileName = item.getName();
				imageMaxSize = item.getSize();

			}
		}
		System.out.println(imageFile.toURL().toString());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("url", imageFile.toURL().toString());
		return new ModelAndView("fileLocation", map);
	}

}
