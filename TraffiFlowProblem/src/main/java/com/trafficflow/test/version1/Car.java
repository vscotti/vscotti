package com.trafficflow.test.version1;

/**
 * This class represent a Car. A car has speed. This speed can't be greater than 30, least than 5 and should be 5, 10, 15, 20, 25 or 30
 * 
 * @author Victor Scotti
 *
 */
public class Car {

	private double tripTime;
	private int speed;

	public Car() {
	}

	public Car(int speed) {
		this.speed = speed;
		this.tripTime = 0;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Method to validate is the speed is valid.
	 */
	public void validate() {
		if(speed < 5) {
			throw new IllegalArgumentException("Speed should be greater or equal to 5");
		}
		if(speed > 30) {
			throw new IllegalArgumentException("Speed should be least or equal to 30");
		}
		if(speed % 5 != 0) {
			throw new IllegalArgumentException("Speed should be 5, 10, 15, 20, 25 or 30");
		}
	}
	
    public void drive(double distance){
        tripTime += distance / getSpeed();
    }

    public void stop(double timeToWait){
        tripTime += timeToWait;
    }

    public double getTimer() {
        return tripTime;
    }
}
