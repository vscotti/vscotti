package com.trafficflow.test.version1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the road/trip of a car. 
 * There is the Car and the lights that this car should pass through.
 * 
 * @author Victor Scotti
 *
 */
public class Trip {

	// Distance between lights
	private static final int DISTANCE_BETWEEN_LIGTHS = 150;
	
	// List of Lights
	private List<Light> lights;

	// The Car
	private Car car;

	public Trip() {
	}

	public Trip(int[] ls, int speed) {
		lights = new ArrayList<Light>();
		for(int i = 0 ; i < ls.length ; i++) {
			lights.add(new Light(i + 1 , ls[i]));
		}
		car = new Car(speed);
	}
	
	public Trip(List<Light> lights, Car car) {
		this.car = car;
		this.lights = lights;
	}
	
	public List<Light> getLights() {
		return lights;
	}

	public void setLights(List<Light> lights) {
		this.lights = lights;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
	/**
	 * Method to calculate the time that takes to go from beginning to end. With validating the information
	 * 
	 * @return the amount of seconds that takes this trip
	 */
	public int run() {
		validate();
		return calculateTripTime();
	}
	
	/**
	 * Method to calculate the time that takes to go from beginning to end.
	 * 
	 * @return the amount of seconds that takes this trip
	 */
	private int calculateTripTime() {
		int speed = car.getSpeed();
		double time = 0;
		// Time to arrive to next light
		double timeTonext = (double) DISTANCE_BETWEEN_LIGTHS / speed;
		// Initial time should be at first light
		time = timeTonext;

		// Move for each light
		for (Light light : lights) {
			// Once car arrive to light should check if the light is green
			boolean isGreen = light.isGreen(time);
			// Calculating waiting time. If light is in green waiting time = 0
			double wt = light.getWaitingTime(time);
			
			System.out.println("time: " + time + " - car arrive at light " + light.getNumber() + " light is " + ((isGreen)?"green":"red"));
			if(!isGreen) {
				System.out.println("time: " + (time + wt) + " - the light turns green, and the car continues.");
			}
			
			// Add the waiting time and time to get next light. 
			time += wt + timeTonext;
		}
		
		System.out.println("time: " + Math.floor(time) + " - the car reaches the end, 150 meters past the last traffic light.");
		
		// Return the round value
		return (int) Math.floor(time);
	}

	/**
	 * This method is to validate that all the information is valid. If any of them is invalid will throw an exception
	 */
	public void validate() {
		// validate that there is a car
		if(car == null) {
			throw new IllegalArgumentException("You need a car for this trip");
		}
		// Validate car speed
		car.validate();
		// Validate if there are at least 1 light
		if(lights == null || lights.size() < 1) {
			throw new IllegalArgumentException("You need at least 1 light in your trip");
		}
		// Validate if there are least than 50 lights
		if(lights.size() > 50) {
			throw new IllegalArgumentException("You can't have more than 50 lights in your trip");
		}
		// validate if the time of each light is valid
		for (Light light : lights) {
			light.validate();
		}
		
	}
}
