package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyResource;

import java.util.List;
import java.util.Optional;

public class LocationProcessor {

    public static Optional<EmergencyResource> findClosestResource(String emergencyLocation, List<EmergencyResource> resources) {
        double[] emergencyCoords = parseLocation(emergencyLocation);
        EmergencyResource closest = null;
        double minDistance = Double.MAX_VALUE;

        for (EmergencyResource resource : resources) {
            double[] resourceCoords = parseLocation(resource.getLocation());
            double distance = calculateDistance(emergencyCoords[0], emergencyCoords[1],
                    resourceCoords[0], resourceCoords[1]);
            if (distance < minDistance) {
                minDistance = distance;
                closest = resource;
            }
        }
        return Optional.ofNullable(closest);
    }


    private static double[] parseLocation(String location) {
        String[] parts = location.split(",");
        double lat = Double.parseDouble(parts[0].trim());
        double lon = Double.parseDouble(parts[1].trim());
        return new double[]{lat, lon};
    }


    private static  double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
