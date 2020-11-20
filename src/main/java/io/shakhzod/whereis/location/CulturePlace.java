package io.shakhzod.whereis.location;

public class CulturePlace {

    private String name;
    private String district;
    private String street;
    private String telephone;
    private String web;
    private String latitude;
    private String longitude;


    public CulturePlace(String name, String district, String street, String telephone, String web, String latitude, String longitude) {
        this.name = name;
        this.district = district;
        this.street = street;
        this.telephone = telephone;
        this.web = web;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        longitude = longitude;
    }
}
