package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
public final class CsvFileConfiguration {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;

	private int smallCityPosition;
	private int largeCityPosition;
	private int statePosition;
	private int countryPosition;
	private int mobilePosition;
	private int typeOfServicePosition;
	private int suscriberPosition;

	@Column(nullable = true)
	private Integer campo1;
	@Column(nullable = true)
	private Integer campo2;
	@Column(nullable = true)
	private Integer campo3;
	@Column(nullable = true)
	private Integer campo4;
	@Column(nullable = true)
	private Integer campo5;
	@Column(nullable = true)
	private Integer campo6;
	@Column(nullable = true)
	private Integer campo7;
	@Column(nullable = true)
	private Integer campo8;
	@Column(nullable = true)
	private Integer campo9;
	@Column(nullable = true)
	private Integer campo10;

	@Column(name = "charSeparator")
	private String separator;
	@Column(nullable = true)
	private String dateFormat;

	@Column(nullable = true)
	private Integer emailPosition;

	private String code;
	private String phonePositions;

	public CsvFileConfiguration() {
	}

	public CsvFileConfiguration(String code, int smallCityPosition,
			int largeCityPosition, int statePosition, int countryPosition,
			String phonePositions, int mobilePosition,
			int typeOfServicePosition, int suscriberPosition, Integer campo1,
			Integer campo2, Integer campo3, Integer campo4, Integer campo5,
			Integer campo6, Integer campo7, Integer campo8, Integer campo9,
			Integer campo10, String separator, String dateFormat,
			Integer emailPosition

	) {
		super();
		this.code = code;
		this.separator = separator;
		this.smallCityPosition = smallCityPosition;
		this.largeCityPosition = largeCityPosition;
		this.statePosition = statePosition;
		this.countryPosition = countryPosition;
		this.phonePositions = phonePositions;
		this.mobilePosition = mobilePosition;
		this.typeOfServicePosition = typeOfServicePosition;
		this.suscriberPosition = suscriberPosition;
		this.dateFormat = dateFormat;
		this.emailPosition = emailPosition;
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

	public int[] getPhonePositionsAsInt() {
		String[] positionsAsStrings = getPhonePositions().split(",");
		int[] positions = new int[positionsAsStrings.length];
		for (int i = 0; i < positionsAsStrings.length; i++) {
			positions[i] = Integer.parseInt(positionsAsStrings[i]);
		}
		return positions;
	}

	public String getCode() {
		return code;
	}

	public int getSmallCityPosition() {
		return smallCityPosition;
	}

	public int getLargeCityPosition() {
		return largeCityPosition;
	}

	public int getStatePosition() {
		return statePosition;
	}

	public int getCountryPosition() {
		return countryPosition;
	}

	public String getPhonePositions() {
		return phonePositions;
	}

	public int getMobilePosition() {
		return mobilePosition;
	}

	public int getTypeOfServicePosition() {
		return typeOfServicePosition;
	}

	public int getSuscriberPosition() {
		return suscriberPosition;
	}

	public Integer getCampo1() {
		return campo1;
	}

	public Integer getCampo2() {
		return campo2;
	}

	public Integer getCampo3() {
		return campo3;
	}

	public Integer getCampo4() {
		return campo4;
	}

	public Integer getCampo5() {
		return campo5;
	}

	public Integer getCampo6() {
		return campo6;
	}

	public Integer getCampo7() {
		return campo7;
	}

	public Integer getCampo8() {
		return campo8;
	}

	public Integer getCampo9() {
		return campo9;
	}

	public Integer getCampo10() {
		return campo10;
	}

	public String getSeparator() {
		return separator;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public Integer getEmailPosition() {
		return emailPosition;
	}

	public void setEmailPosition(Integer emailPosition) {
		this.emailPosition = emailPosition;
	}

}
