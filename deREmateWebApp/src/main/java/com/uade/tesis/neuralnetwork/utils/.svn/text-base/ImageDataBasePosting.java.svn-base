package com.uade.tesis.neuralnetwork.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import com.uade.tesis.domains.Posting;
import com.uade.tesis.neuralnetwork.bordes.SobelBorderFactory;
import com.uade.tesis.neuralnetwork.som.utils.ThumbnailImage;

public class ImageDataBasePosting {

	private static final long serialVersionUID = 1L;
	
    private Map<FileInfo,ThumbnailImage> thumbnailImageMap;
	private boolean needBorderProcessing;
	
    public ImageDataBasePosting(List<Posting> postings, boolean needBorderProcessing) {
    	loadImageDataBase(postings, needBorderProcessing);
    }
    
    public void loadImageDataBase(List<Posting> postings, boolean needBorderProcessing) {
    	this.needBorderProcessing = needBorderProcessing;
    	this.thumbnailImageMap = new HashMap<FileInfo, ThumbnailImage>();
        doCreateImageDataBase(postings);
    }

	private void doCreateImageDataBase(List<Posting> postings) {
		for(Posting posting : postings) {
			if(posting.getFileImages() != null &&
					posting.getFileImages().size() > 0) {
				File file = new File(posting.getFileImages().get(0));
				if(file.exists()) {
		    		if((isImageFile(file) == true) && (this.isFileAlreadyInserted(file) == false)) {
		    			boolean noerror = true;
		    			String processedPath = "";
		    			try {
		    				processedPath = file.getCanonicalPath();
		    			} catch(IOException exp) { noerror = false; }
		    			if(noerror) {
		                	File processedFile = SobelBorderFactory.processImage(file, "C:/Documents and Settings/Administrador/Escritorio/bordes/", true);
							try {
								processedPath = processedFile.getCanonicalPath();
								ThumbnailImage thumnailImage = new ThumbnailImage(processedPath, posting);
			    				thumbnailImageMap.put(new FileInfo(file,processedFile), thumnailImage);
							} catch (IOException e) {
								e.printStackTrace();
							}
		    			}
		    		}
		    	}
			}
		}
    }
    
    private boolean isImageFile(File f) {
        boolean isImageFile = true;
        try {
            ImageIcon img = new ImageIcon(f.getCanonicalPath());
            if(img.getIconHeight() <= 0 || img.getIconWidth() <= 0) {
                isImageFile = false;
            }
        }
        catch(IOException exp) {
            isImageFile = false;            
        }
        return isImageFile;
    }

    public File getRealImage(String processedFileName) {
		Iterator<Entry<FileInfo, ThumbnailImage>> it = getThumbnailImageMap().entrySet().iterator();
		while(it.hasNext()) {
			FileInfo info = it.next().getKey();
			File f = info.getProcessedFile();
			try {
				if(f != null && f.getCanonicalPath().equals(processedFileName)) {
					return info.getFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
    }
    
    private boolean isFileAlreadyInserted(File file) {
        return thumbnailImageMap.containsKey(new FileInfo(file,null));
    }

	public Map<FileInfo, ThumbnailImage> getThumbnailImageMap() {
		return thumbnailImageMap;
	}
	public void setThumbnailImageMap(Map<FileInfo, ThumbnailImage> thumbnailImageMap) {
		this.thumbnailImageMap = thumbnailImageMap;
	}
	public boolean isNeedBorderProcessing() {
		return needBorderProcessing;
	}
	public void setNeedBorderProcessing(boolean needBorderProcessing) {
		this.needBorderProcessing = needBorderProcessing;
	}
    
}
