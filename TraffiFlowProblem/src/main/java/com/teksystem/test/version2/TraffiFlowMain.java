package com.teksystem.test.version2;

public class TraffiFlowMain {

	public static void main(String[] args) {
		int[] a = {10,11,12,13,14,15};
		int s = 5;
		double time = 0;
		double timeTonext = 150.0 / s;
		time = timeTonext;
		for (int i = 0; i < a.length; i++) {
			boolean ison = ((int)(time / a[i]) % 2) == 0;
			System.out.println("car arrive at light " + (i+1) + " time: " + time + " light is " + ((ison)?"On":"Off"));
			if(!ison) {
				int wt = (((int)(time / a[i]))+1)*a[i]-(int)time;
				System.out.println("waitig: " + wt);
				time += wt;
			}
			time += timeTonext;
		}
		System.out.println(time);
	}
}
