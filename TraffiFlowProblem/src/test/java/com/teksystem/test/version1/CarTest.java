package com.teksystem.test.version1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CarTest {
    private Car car;

    @Before
    public void createSimulation() {
        car = new Car();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSetSpeed() {
        car.setSpeed(10);
        Assert.assertTrue(true);
    }

    @Test
    public void testGetSpeed() {
        car.setSpeed(10);
        int speed = car.getSpeed();
        Assert.assertTrue(speed == 10);
    }

    @Test
    public void testValidSpeed() {
        car.setSpeed(10);
        car.validate();
        Assert.assertTrue(true);
    }

    @Test
    public void testInvalidSpeed() {
        expectedException.expect(IllegalArgumentException.class);
        car.setSpeed(11);
        car.validate();
    }

    @Test
    public void testInvalidLeastThan5Speed() {
        expectedException.expect(IllegalArgumentException.class);
        car.setSpeed(1);
        car.validate();
    }

    @Test
    public void testInvalidGreaterThan30Speed() {
        expectedException.expect(IllegalArgumentException.class);
        car.setSpeed(56);
        car.validate();
    }
}
