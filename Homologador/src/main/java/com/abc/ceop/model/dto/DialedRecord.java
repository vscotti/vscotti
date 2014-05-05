package com.abc.ceop.model.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DialedRecord {
	
	private Long callerId;
	private String idLlamada;
	private Date callDate;
	private String source;
	private String arbol;
	
	private Map<DialedOption, Integer> dialedValues = new LinkedHashMap<DialedOption, Integer>();
	
	public DialedRecord () {
	}
	
	public DialedRecord(Long callerId) {
		this.callerId = callerId;
	}
	
	public DialedRecord(Long callerId, Map<DialedOption, Integer> dialedValues, String idLlamada, Date callDate, String source) {
		this.callerId = callerId;
		this.dialedValues = dialedValues;
		this.idLlamada = idLlamada;
		this.callDate = callDate;
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getCallerId() {
		return callerId;
	}

	public Map<DialedOption, Integer> getDialedValues() {
		return dialedValues;
	}

	public void setCallerId(Long callerId) {
		this.callerId = callerId;
	}

	public void setDialedValues(Map<DialedOption, Integer> dialedValues) {
		this.dialedValues = dialedValues;
	}

	public String getIdLlamada() {
		return idLlamada;
	}

	public void setIdLlamada(String idLlamada) {
		this.idLlamada = idLlamada;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getArbol() {
        return arbol;
    }

    public void setArbol(String arbol) {
        this.arbol = arbol;
    }

    @Override
	public String toString() {
		return callerId + ", " + idLlamada  + ", " + Arrays.toString(dialedValues.values().toArray());
	}
	
}
