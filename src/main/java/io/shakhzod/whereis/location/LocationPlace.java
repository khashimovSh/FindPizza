package io.shakhzod.whereis.location;


import java.util.UUID;

public class LocationPlace {

    private UUID uuid;
    private String name;
    private double longitude;
    private double latitude;
    private String address;
    private String web;
    private String hours;

    public LocationPlace(){

    }

    public LocationPlace(String name, double latitude, double longitude, String address, String web, String hours) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address=address;
        this.web=web;
        this.hours=hours;
    }

    public LocationPlace(UUID id, String name, double latitude, double longitude) {
        this.uuid = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getWeb() {
        return web;
    }

    public String getHours() {
        return hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
