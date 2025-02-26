package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.service.LocationProcessor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertNull;

public class LocationProcessorTest {

    @Test
    public void findClosestResourceShouldReturnSingleResourceWhenOneElement() {
        // given
        String emergencyLocation = "52.2297,21.0122";
        List<EmergencyResource> resources = new ArrayList<>();
        resources.add(new EmergencyResource("53.2297,20.0122"));

        // when
        EmergencyResource result = LocationProcessor.findClosestResource(emergencyLocation, resources);

        // then
        assertNotNull(result);
        assertEquals("53.2297,20.0122", result.getLocation());
    }

    @Test
    public void findClosestResourceShouldReturnClosestWhenMultipleResources() {
        // given
        String emergencyLocation = "52.2297,21.0122"; // Warszawa


        List<EmergencyResource> resources = new ArrayList<>();
        resources.add(new EmergencyResource("54.372158,18.638306")); // Gdańsk
        resources.add(new EmergencyResource("50.064650,19.944980")); // Kraków
        resources.add(new EmergencyResource("52.520008,13.404954")); // Berlin

        // when
        EmergencyResource result = LocationProcessor.findClosestResource(emergencyLocation, resources);

        // then
        assertNotNull(result);
        assertEquals("50.064650,19.944980", result.getLocation());
    }

    @Test
    public void findClosestResource_shouldHandleSameLocation() {
        // given
        String emergencyLocation = "52.2297,21.0122";
        List<EmergencyResource> resources = new ArrayList<>();
        resources.add(new EmergencyResource("52.2297,21.0122"));
        resources.add(new EmergencyResource("53.0,20.0"));

        // when
        EmergencyResource result = LocationProcessor.findClosestResource(emergencyLocation, resources);

        // then
        assertNotNull(result);
        assertEquals("52.2297,21.0122", result.getLocation());
    }
}

