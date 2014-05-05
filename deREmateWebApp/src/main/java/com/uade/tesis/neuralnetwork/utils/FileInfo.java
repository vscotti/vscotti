package com.uade.tesis.neuralnetwork.utils;

import java.io.File;

public class FileInfo {
	
    private File file;
    private File processedFile;

    public FileInfo(File file, File processedFile) {
        this.file = file;
        this.processedFile = processedFile;
    }
    
    public File getProcessedFile() {
		return processedFile;
	}

	public File getFile() {
        return file;
    }
    
    public String toString() {
        return file.getName();
    }
    
    public boolean equals(Object obj) {
        return file.equals(((FileInfo)obj).getFile());
    }

    public int hashCode() {
        return file.hashCode();
    }
}
