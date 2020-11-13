package io.shakhzod.whereis.location;

import io.shakhzod.whereis.service.LocationsService;

import java.util.ArrayList;
import java.util.List;

public class LocationFilter {

    private double radLat;  // latitude in radians
    private double radLon;  // longitude in radians

    private double degLat;  // latitude in degrees
    private double degLon;  // longitude in degrees

    private static final double MIN_LAT = Math.toRadians(-90d);  // -PI/2
    private static final double MAX_LAT = Math.toRadians(90d);   //  PI/2
    private static final double MIN_LON = Math.toRadians(-180d); // -PI
    private static final double MAX_LON = Math.toRadians(180d);  //  PI

    public LocationFilter () {
    }



    public LocationFilter(double latitude, double longitude){
        this.radLat = Math.toRadians(latitude);
        this.radLon = Math.toRadians(longitude);
        this.degLat = latitude;
        this.degLon = longitude;
    }



    @Override
    public String toString() {
        return "LocationFilter{" +
                "radLat=" + radLat +
                ", radLon=" + radLon +
                ", degLat=" + degLat +
                ", degLon=" + degLon +
                '}';
    }

    public double getRadLat() {
        return radLat;
    }

    public void setRadLat(double radLat) {
        this.radLat = radLat;
    }

    public double getRadLon() {
        return radLon;
    }

    public void setRadLon(double radLon) {
        this.radLon = radLon;
    }

    public double getDegLat() {
        return degLat;
    }

    public void setDegLat(double degLat) {
        this.degLat = degLat;
    }

    public double getDegLon() {
        return degLon;
    }

    public void setDegLon(double degLon) {
        this.degLon = degLon;
    }
}
