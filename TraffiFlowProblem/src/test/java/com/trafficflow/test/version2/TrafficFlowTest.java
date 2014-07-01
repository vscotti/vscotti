package com.trafficflow.test.version2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.trafficflow.test.version2.TraffiFlow;

public class TrafficFlowTest {
	
    private TraffiFlow tf;

    @Before
    public void createSimulation() {
        tf = new TraffiFlow();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testValidSpeed() {
    	int[] a = {10};
    	tf.validateData(a, 5);
    	Assert.assertTrue(true);
    }

    @Test
    public void testInvalidSpeed1() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10};
    	tf.validateData(a, 1);
    }

    @Test
    public void testInvalidSpeed2() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10};
    	tf.validateData(a, 6);
    }

    @Test
    public void testInvalidSpeed3() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10};
    	tf.validateData(a, 45);
    }

    @Test
    public void testInvalidEmptyLights() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {};
    	tf.validateData(a, 30);
    }

    @Test
    public void testInvalidLights1() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {1};
    	tf.validateData(a, 30);
    }

    @Test
    public void testInvalidLights2() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {69};
    	tf.validateData(a, 30);
    }

    @Test
    public void testInvalidMoreThan50Lights() {
        expectedException.expect(IllegalArgumentException.class);
    	int[] a = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
    	tf.validateData(a, 30);
    }

    @Test
    public void testValidRun() {
    	int[] a = {10,10,10};
    	int triptime = tf.runTrip(a, 5);
    	Assert.assertTrue(triptime == 150);
    }

}
