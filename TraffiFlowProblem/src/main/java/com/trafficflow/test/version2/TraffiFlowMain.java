package com.trafficflow.test.version2;

import com.trafficflow.test.commons.TrafficFlow;

public class TraffiFlowMain {

	public static void main(String[] args) {
		int[] a = {10,11,12,13,14,15};
		int s = 5;
		
		TrafficFlow tf = new Trip(a, s);
		tf.run();
	}
}
