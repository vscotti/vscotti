package com.abc.ceop.model.dto;

public class FirstProcessStats {

	private int correctedPhonesCount = 0;
	private int invalidPhonesCount = 0;
	private int duplicatedPhonesCount = 0;
	private int invalidLocationsCount = 0;
	
	public void incrementCorrectedPhonesCount() {
		correctedPhonesCount++;
	}
	
	public void incrementInvalidPhonesCount() {
		invalidPhonesCount++;
	}
	
	public void incrementDuplicatedPhonesCount() {
		duplicatedPhonesCount++;
	}
	
	public void incrementInvalidLocationsCount() {
		invalidLocationsCount++;
	}
	
	public int getCorrectedPhonesCount() {
		return correctedPhonesCount;
	}

	public int getInvalidPhonesCount() {
		return invalidPhonesCount;
	}

	public int getDuplicatedPhonesCount() {
		return duplicatedPhonesCount;
	}

	public int getInvalidLocationsCount() {
		return invalidLocationsCount;
	}
	
}
