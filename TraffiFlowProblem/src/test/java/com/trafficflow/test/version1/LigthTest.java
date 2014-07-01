package com.trafficflow.test.version1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.trafficflow.test.version1.Light;

public class LigthTest {
    private Light light;

    @Before
    public void createSimulation() {
    	light = new Light();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void testSetDuration() {
        light.setDuration(10);
        Assert.assertTrue(true);
    }

    @Test
    public void testGetDuration() {
    	light.setDuration(10);
        int duration = light.getDuration();
        Assert.assertTrue(duration == 10);
    }
    
    @Test
    public void testValidDuration() {
        light.setDuration(15);
        light.validate();
        Assert.assertTrue(true);
    }

    @Test
    public void testInvalidLeastThan10Duration() {
        expectedException.expect(IllegalArgumentException.class);
        light.setDuration(5);
        light.validate();
    }

    @Test
    public void testInvalidGreaterThan60Duration() {
        expectedException.expect(IllegalArgumentException.class);
        light.setDuration(89);
        light.validate();
    }
}
