package com.trafficflow.test.version1;

import com.trafficflow.test.commons.Observer;

/**
 * This class represent a Light. A light has number and duration. The number is the unique identification and the duration is the time that takes to change.
 * This duration can't be greater than 60 and least than 10
 * 
 * @author Victor Scotti
 *
 */
public class Light implements Observer {

	// Id
	private int number;

	// Time to change the light
	private int duration;

	// Status indicator 
	private boolean isGreen;
	private double remainingTimeNexChange;
	
	private static enum LightStatus {GREEN, RED};

	public Light() {
	}
	
	public Light(int number, int duration) {
		this.duration = duration;
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public boolean isGreen() {
		return isGreen;
	}
	
	/**
	 * This method returns the waiting time of a light. If the light is green should not be waiting time.
	 * To calculate this value I use how many changes should have the light at this time. This is with time / duration.
	 * I cast it to int to get the integer value. With this value I add 1 to put the index on next change. I multiply
	 * this value with the duration and I'll get the total time for next change. If I deduct the time to this value
	 * I'll got the remaining time.
	 * IE:
	 * 		Arrive time: 65
	 * 		Duration: 15
	 * 
	 * 		Changes of the light: 4.33 = 4. This means that light have changed 4 times on this time.
	 * 		Next change: 4 + 1 = 5. To do 5 changes light takes 75 = 5 * 15
	 * 
	 * 		Waiting time: 10 = 75 - 65
	 * 
	 * @param time - Represents the time that wants to check
	 * 
	 * @return waiting time
	 */
	public double getRemainingTimeNextChange() {
		return remainingTimeNexChange;
	}
	
	/**
	 * Method to validate if the duration is valid
	 */
	public void validate() {
		if(duration < 10) {
			throw new IllegalArgumentException("Duration should be greater or equal to 10");
		}
		if(duration > 60) {
			throw new IllegalArgumentException("Duration should be least or equal to 60");
		}
	}

	/**
	 * This method return is the light is green or not for a period of time.
	 * To do this, it used time / duration. This value represents how many changes did the light. With module 2 get is the 
	 * light is on 0 or 1. O = Green and 1 = Red
	 * 
	 * @param time - Represents the time that wants to check
	 * 
	 * @return is green or not
	 */
	public void update(double time) {
		isGreen = ((int)(time / duration) % 2) == LightStatus.GREEN.ordinal();
		remainingTimeNexChange = 0.0;
		if(!isGreen()) {
			// Calculating waiting time. If light is in green waiting time = 0
			remainingTimeNexChange = (double)(((int)(time / duration) + 1) * duration) - time;
		} 

	}
}
