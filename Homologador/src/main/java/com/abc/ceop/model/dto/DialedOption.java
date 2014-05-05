package com.abc.ceop.model.dto;

public class DialedOption {
	
	private Integer node;
	private String column;
	private String columnSynonym;
	
	public DialedOption() {
		
	}
	
	public DialedOption (Integer node, String column){
		this.node = node;
		this.column = column;
	}
	
	public DialedOption (Integer node, String column, String columnSynonym){
		this.node = node;
		this.column = column;
		this.columnSynonym = columnSynonym;
	}
	
	public DialedOption (String column) {
		this.column = column;
	}

	public String getColumnSynonym() {
		return columnSynonym;
	}

	public Integer getNode() {
		return node;
	}

	public String getColumn() {
		return column;
	}

	public String toString(){
		return node + " " + column;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof DialedOption) {
			if (column.equals(((DialedOption)obj).column) && node.equals(((DialedOption)obj).node)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return node.hashCode() + column.hashCode();
	}
	
}
