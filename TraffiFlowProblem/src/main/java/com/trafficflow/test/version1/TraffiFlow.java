package com.trafficflow.test.version1;

public class TraffiFlow {

	public static void main(String[] args) {
//		int[] a = {10,10,10};
//		int s = 30;
////		Returns: 30

//		int[] a = {10,10,10}; 
//		int s = 20;
////		Returns: 35

//		int[] a = {10,20,30};
//		int s = 20;
////		Returns: 35

		int[] a = {10,11,12,13,14,15};
		int s = 5;
//		Returns: 240

//		int[] a = {60,60,60,60,60,60,60,60,60,60};
//		int s = 5;
////		Returns: 630

//		int[] a = {55,29,26,12,19,39,18,20,23,28,56,20,59,48,33,40,30,60,19};
//		int s = 25;
////		Returns: 252
		
		Trip trip = new Trip(a, s);
		System.out.println(trip.run());
	}
}
