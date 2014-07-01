package com.trafficflow.test.version1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.trafficflow.test.version1.Car;
import com.trafficflow.test.version1.Light;
import com.trafficflow.test.version1.Trip;

public class TripTest {
    private Trip trip;

    @Before
    public void createSimulation() {
        trip = new Trip();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSetCar() {
        trip.setCar(new Car());
        Assert.assertTrue(true);
    }

    @Test
    public void testGetCar() {
        trip.setCar(new Car());
        Car car = trip.getCar();
        Assert.assertNotNull(car);
    }

    @Test
    public void testSetLights() {
        trip.setLights(new ArrayList<Light>());
        Assert.assertTrue(true);
    }

    @Test
    public void testGetLights() {
        trip.setLights(new ArrayList<Light>());
        List<Light> lights = trip.getLights();
        Assert.assertNotNull(lights);
    }

    @Test
    public void testMissingCar() {
        expectedException.expect(IllegalArgumentException.class);
        trip = new Trip();
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light());
        trip.setLights(lights);
        trip.validate();
    }

    @Test
    public void testMissingLights() {
        expectedException.expect(IllegalArgumentException.class);
        trip = new Trip();
        trip.setCar(new Car(5));
        trip.validate();
    }

    @Test
    public void testMissingEmptyLights() {
        expectedException.expect(IllegalArgumentException.class);
        trip = new Trip();
        trip.setCar(new Car(5));
        trip.setLights(new ArrayList<Light>());
        trip.validate();
    }

    @Test
    public void testMissingMoreThan50Lights() {
        expectedException.expect(IllegalArgumentException.class);
        trip = new Trip();
        trip.setCar(new Car(5));
        List<Light> lights = new ArrayList<Light>();
        for (int i = 0 ; i < 60 ; i++) {
            lights.add(new Light());
		}
        trip.setLights(lights);
        trip.validate();
    }

    @Test
    public void testInvalidCar() {
        expectedException.expect(IllegalArgumentException.class);
        trip = new Trip();
        trip.setCar(new Car(1));
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light());
        trip.setLights(lights);
        trip.validate();
    }

    @Test
    public void testInvalidLight() {
        expectedException.expect(IllegalArgumentException.class);
        trip = new Trip();
        trip.setCar(new Car(5));
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(1,1));
        trip.setLights(lights);
        trip.validate();
    }

    @Test
    public void testValidRun() {
        trip = new Trip();
        trip.setCar(new Car(5));
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(1,10));
        lights.add(new Light(2,10));
        lights.add(new Light(3,10));
        trip.setLights(lights);
        int tripTime = trip.run();
        Assert.assertTrue(tripTime == 150);
    }
    
}
