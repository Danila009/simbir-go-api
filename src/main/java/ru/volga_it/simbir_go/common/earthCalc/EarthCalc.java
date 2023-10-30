package ru.volga_it.simbir_go.common.earthCalc;


import static java.lang.Math.*;

public class EarthCalc {

    public static final double EARTH_RADIUS = 6_356_752.314245D; // radius at the poles, meters

    public static double distanceInKilometers(
            Double standLatitude,
            Double standLongitude,
            Double foreLatitude,
            Double foreLongitude
    ) {
        double distanceMeters = distance(standLatitude, standLongitude, foreLatitude, foreLongitude);
        return distanceMeters / 1000;
    }

    public static double distance(
            Double standLatitude,
            Double standLongitude,
            Double foreLatitude,
            Double foreLongitude
    ) {

        var Δλ = toRadians(abs(foreLongitude - standLongitude));
        var φ1 = toRadians(standLatitude);
        var φ2 = toRadians(foreLatitude);

        //spherical law of cosines
        var sphereCos = (sin(φ1) * sin(φ2)) + (cos(φ1) * cos(φ2) * cos(Δλ));
        var c = acos(max(min(sphereCos, 1d), -1d));

        return EARTH_RADIUS * c;
    }

}
