package com.uade.tesis.neuralnetwork.utils.older;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import com.uade.tesis.neuralnetwork.bordes.SobelBorderFactory;
import com.uade.tesis.neuralnetwork.som.utils.ThumbnailImage;
import com.uade.tesis.neuralnetwork.utils.FileInfo;

public class ImageDataBase {

	private static final long serialVersionUID = 1L;
	
    private Map<FileInfo,ThumbnailImage> thumbnailImageMap;
	private boolean needBorderProcessing;
	
    public ImageDataBase(String filePath, boolean needBorderProcessing) {
    	loadImageDataBase(filePath, needBorderProcessing);
    }
    
    public void loadImageDataBase(String filePath, boolean needBorderProcessing) {
    	this.needBorderProcessing = needBorderProcessing;
    	this.thumbnailImageMap = new HashMap<FileInfo, ThumbnailImage>();
        doCreateImageDataBase(new File(filePath));
    }

	private void doCreateImageDataBase(File file) {
    	if(file.isDirectory() && file.exists()) {
    		try {
    			List<File> l = getFileListing(file);
                for(int i=0; i<l.size(); i++) {
                	File f = (File)l.get(i);
                    if(f.isDirectory()) {
                    	continue;
                    }
                        
                    if((isImageFile(f) == true) && (isFileAlreadyInserted(f) == false)) {
                        boolean noerror = true;
                        String path = "";
                        try {
                        	path = f.getCanonicalPath();
                        } catch(IOException exp) { noerror = false; }
                            
                        if(noerror) {
                        	File f2 = null;
                        	if(needBorderProcessing) {
                        		f2 = SobelBorderFactory.processImage(f, "C:/Documents and Settings/Administrador/Escritorio/bordes/", true);
                        	} else {
                        		f2 = f;
                        	}
                        	try {
								path = f2.getCanonicalPath();
	                        	ThumbnailImage thumnailImage = new ThumbnailImage(path);
	                        	thumbnailImageMap.put(new FileInfo(f,f2), thumnailImage);
							} catch (IOException e) {
								e.printStackTrace();
							}
                        }
                    }
                        
                        
                }
                    
    		} catch(FileNotFoundException exp) {}
    	} else if(file.exists()) {
    		if((isImageFile(file) == true) && (this.isFileAlreadyInserted(file) == false)) {
    			boolean noerror = true;
    			String path = "";
    			try {
    				path = file.getCanonicalPath();
    			} catch(IOException exp) { noerror = false; }
    			if(noerror) {
                	File f2 = SobelBorderFactory.processImage(file, "C:/Documents and Settings/Administrador/Escritorio/bordes/", true);
					try {
						path = f2.getCanonicalPath();
						ThumbnailImage thumnailImage = new ThumbnailImage(path);
	    				thumbnailImageMap.put(new FileInfo(file,f2), thumnailImage);
					} catch (IOException e) {
						e.printStackTrace();
					}
    			}
    		}
    	}
    }
    
	private List<File> getFileListing(File aStartingDir) throws FileNotFoundException {
        validateDirectory(aStartingDir);
        List<File> result = new ArrayList<File>();
        File[] filesAndDirs = aStartingDir.listFiles();
        List<File> filesDirs = Arrays.asList(filesAndDirs);
        Iterator<File> filesIter = filesDirs.iterator();
        File file = null;
        while(filesIter.hasNext()) {
        	file = (File)filesIter.next();
        	result.add(file);
        	if(!file.isFile()) {
        		List<File> deeperList = getFileListing(file);
        		result.addAll(deeperList);
        	}
        }
        Collections.sort(result);
        return result;
    }

    private void validateDirectory(File aDirectory) throws FileNotFoundException {
        if(aDirectory == null) {
            throw new IllegalArgumentException("Directory should not be null.");
        }
        if(!aDirectory.exists()) {
            throw new FileNotFoundException("Directory does not exist: " + aDirectory);
        }
        if(!aDirectory.isDirectory()) {
            throw new IllegalArgumentException("Is not a directory: " + aDirectory);
         }
        if(!aDirectory.canRead()) {
            throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
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
