package com.trafficflow.test.version2;

public class TraffiFlow {

	public static void main(String[] args) {
		int[] a = {10,11,12,13,14,15};
		int s = 5;
		TraffiFlow tf = new TraffiFlow();
		tf.validateData(a, s);
		tf.runTrip(a, s);
	}
	
	public void validateData(int[] lights, int speed) {
		if(speed < 5) {
			throw new IllegalArgumentException("Speed must be equal or greater than 5");
		}
		if(speed > 30) {
			throw new IllegalArgumentException("Speed must be equal or least than 30");
		}
		if(speed % 5 != 0) {
			throw new IllegalArgumentException("Speed must be 5, 10, 15, 20, 25 or 30");
		}
		if(lights.length == 0) {
			throw new IllegalArgumentException("There must be at least 1 light");
		}
		if(lights.length > 50) {
			throw new IllegalArgumentException("There must be least than 51 light");
		}
		for (int light : lights) {
			if(light < 10) {
				throw new IllegalArgumentException("Light duration must be greateer than 9 light");
			}
			if(light > 60) {
				throw new IllegalArgumentException("Light duration must be least than 61 light");
			}
		}
	}
	
	public int runTrip(int[] lights, int speed) {
		double time = 0;
		double timeTonext = 150.0 / speed;
		time = timeTonext;
		for (int i = 0; i < lights.length; i++) {
			boolean isGreen = ((int)(time / lights[i]) % 2) == 0;
			System.out.println("time: " + time + " - car arrive at light " + (i+1) + " light is " + ((isGreen)?"green":"red"));
			if(!isGreen) {
				int waitingtime = (((int)(time / lights[i]))+1)*lights[i]-(int)time;
				System.out.println("time: " + (time + waitingtime) + " - the light turns green, and the car continues.");
				time += waitingtime;
			}
			time += timeTonext;
		}
		System.out.println("time: " + Math.floor(time) + " - the car reaches the end, 150 meters past the last traffic light.");
		return (int) Math.floor(time);
	}
}
