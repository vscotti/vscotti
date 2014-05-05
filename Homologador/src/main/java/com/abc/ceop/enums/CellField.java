package com.abc.ceop.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author DPerez
 *
 */
public enum CellField {

    CALLER_ID_CELL (0),
    ANI_TELEFONO (2),
    NODE_CELL (5),
    READ_VAR_EVAL_COND (6),
    ANSWER_CELL (9),
    DATA_CELL (8),
    DATE_CELL (10),
    ARBOL_CELL (4);
    
    private final int value;
    
    private static final Map<Integer, CellField> lookup = new HashMap<Integer, CellField>();

    static {
        for (CellField cellField : EnumSet.allOf(CellField.class))
            lookup.put(cellField.getValue(), cellField);
    }
    
    public static CellField get(int code) {
        return lookup.get(code); 
    }
    
    CellField (int val){
        value = val;
    }

    public int getValue() {
        return value;
    }
    
}
