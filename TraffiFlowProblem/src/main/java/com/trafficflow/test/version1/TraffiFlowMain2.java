package com.trafficflow.test.version1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.trafficflow.test.commons.TrafficFlow;

public class TraffiFlowMain2 {

	public static void main(String[] args) {
		try{
			boolean valid = false;
			String line = "";
			int s = 0;
			int[] a = {};
			while(!valid) {
				System.out.print("Enter lights (comma separated):");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				line = br.readLine(); 
				String[] lights = line.split(",");
				try {
					List<Integer> list = new ArrayList<Integer>();
					for (String l : lights) {
						list.add(Integer.parseInt(l.trim()));
					}
					valid = true;
					Integer[] aux = list.toArray(new Integer[list.size()]);
					a = new int[aux.length];
					for (int i = 0 ; i < aux.length ; i++) {
						a[i] = aux[i];
					}
				} catch(NumberFormatException e) {
					System.out.println("Data error, Please enter again the information.");
				}
			}
			valid = false;
			while(!valid) {
				System.out.print("Enter speed:");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				line = br.readLine();
				try {
					s = Integer.parseInt(line);
					valid = true;
				} catch(NumberFormatException e) {
					System.out.println("Data error, Please enter again the information.");
				}
			}
			TrafficFlow trip = new Trip(a, s);
			trip.run();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}