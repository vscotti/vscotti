package com.abc.ceop.model.dto;

import java.util.Collections;
import java.util.List;

import com.abc.ceop.model.entities.Location;

public class Record {

	private Location location;
	private List<String> telephones;
	private String mobile;
	private String header;
	private String rowValues;
	private String typeOfService;
	private String suscriber;
	private String email;

	private String campo1;
	private String campo2;
	private String campo3;
	private String campo4;
	private String campo5;
	private String campo6;
	private String campo7;
	private String campo8;
	private String campo9;
	private String campo10;

	public Record(Location location
			  	  ,String mobile
				  ,List<String> telephones
				  ,String header
				  ,String rowValues
				  ,String typeOfService
				  ,String suscriber
				  ,String email
				  ,String campo1
				  ,String campo2
				  ,String campo3
				  ,String campo4
				  ,String campo5
				  ,String campo6
				  ,String campo7
				  ,String campo8
				  ,String campo9
				  ,String campo10
				  ) {
		this.location = location;
		this.telephones = telephones;
		this.mobile = mobile;
		this.header = header;
		this.rowValues = rowValues;
		this.typeOfService = typeOfService;
		this.suscriber = suscriber;
		this.email = email;
		this.campo1 = campo1;
		this.campo2 = campo2;
		this.campo3 = campo3;
		this.campo4 = campo4;
		this.campo5 = campo5;
		this.campo6 = campo6;
		this.campo7 = campo7;
		this.campo8 = campo8;
		this.campo9 = campo9;
		this.campo10 = campo10;
	}
	
	public Location getLocation() {
		return location;
	}
		public List<String> getTelephones() {
		return Collections.unmodifiableList(telephones);
	}
	public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	public String getMobile() {
		return mobile;
	}
	public String getHeader() {
		return header;
	}
	public String getRowValues() {
		return rowValues;
	}
	public String getTypeOfService() {
		return typeOfService;
	}
	public String getSuscriber() {
		return suscriber;
	}
	public void setTelephones(List<String> telephones) {
		this.telephones = telephones;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCampo1() {
		return campo1;
	}
	public String getCampo2() {
		return campo2;
	}
	public String getCampo3() {
		return campo3;
	}
	public String getCampo4() {
		return campo4;
	}
	public String getCampo5() {
		return campo5;
	}
	public String getCampo6() {
		return campo6;
	}
	public String getCampo7() {
		return campo7;
	}
	public String getCampo8() {
		return campo8;
	}
	public String getCampo9() {
		return campo9;
	}
	public String getCampo10() {
		return campo10;
	}	
}
