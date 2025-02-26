package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyResource;

import java.util.List;

public class LocationProcessor {

    /**
     * Finds the EmergencyResource closest to a given location.
     *
     * @param emergencyLocation  a string representing the location (e.g., "52.2297,21.0122")
     * @param resources a list of resources to search
     * @return the EmergencyResource nearest to the location
     */
    public static EmergencyResource findClosestResource(String emergencyLocation, List<EmergencyResource> resources) {
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
        return (closest);
    }

    /**
     * Parses a string representing a location (e.g., "52.2297,21.0122")
     * into a double array where [0] is latitude and [1] is longitude.
     *
     * @param location the string to parse
     * @return a double array [lat, lon]
     */
    private static double[] parseLocation(String location) {
        String[] parts = location.split(",");
        double lat = Double.parseDouble(parts[0].trim());
        double lon = Double.parseDouble(parts[1].trim());
        return new double[]{lat, lon};
    }

    /**
     * Calculates the distance in kilometers between two coordinates,
     * possibly using the Haversine formula.
     *
     * @param lat1 latitude of the first point
     * @param lon1 longitude of the first point
     * @param lat2 latitude of the second point
     * @param lon2 longitude of the second point
     * @return the distance in kilometers between the two points
     */
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
