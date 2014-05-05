package com.abc.ceop.enums;

/**
 * 
 * @author DPerez
 * 
 */
public enum CellValue {

    READ_VAR("ReadVar"), EVAL_COND("EvalCond");

    private final String value;

    CellValue(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

}
