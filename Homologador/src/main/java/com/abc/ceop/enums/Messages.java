package com.abc.ceop.enums;

public enum Messages {

    EXCEL ("Un error a ocurrido en DialRecordExcelFileReaderImpl / readExcel: {} / {}");
    
    private final String value;
    
    Messages (String v){
        this.value = v;
    }

    public String getValue() {
        return value;
    }
    
}
