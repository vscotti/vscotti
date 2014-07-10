package com.trafficflow.test.version2;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.trafficflow.test.commons.TrafficFlow;

public class TrafficFlowTest {
	
    private TrafficFlow tf;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testValidSpeed() {
    	int[] a = {10};
        tf = new Trip(a, 5);
    	tf.validate();
    	Assert.assertTrue(true);
    }

    @Test
    public void testInvalidSpeed1() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10};
        tf = new Trip(a, 1);
    	tf.validate();
    }

    @Test
    public void testInvalidSpeed2() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10};
        tf = new Trip(a, 6);
    	tf.validate();
    }

    @Test
    public void testInvalidSpeed3() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10};
        tf = new Trip(a, 45);
    	tf.validate();
    }

    @Test
    public void testInvalidEmptyLights() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {};
        tf = new Trip(a, 30);
    	tf.validate();
    }

    @Test
    public void testInvalidLights1() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {1};
        tf = new Trip(a, 30);
    	tf.validate();
    }

    @Test
    public void testInvalidLights2() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {69};
        tf = new Trip(a, 30);
    	tf.validate();
    }

    @Test
    public void testInvalidMoreThan50Lights() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
        tf = new Trip(a, 30);
    	tf.validate();
    }

    @Test
    public void testValidRun() {
    	int[] a = {10,10,10};
        tf = new Trip(a, 5);
    	tf.validate();
    	int triptime = tf.run();
    	Assert.assertTrue(triptime == 150);
    }

}
