package dtos;

import entities.Location;


public class LocationDTO {

    private Long id;
    private String address;
    private String city;
    private String zipCode;
    private String country;
    private String longitude;
    private String latitude;

    public LocationDTO(Long id, String address, String city, String zipCode, String country, String longitude, String latitude) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public LocationDTO(Location location) {
        this.id = location.getId();
        this.address = location.getAddress();
        this.city = location.getCity();
        this.zipCode = location.getZipCode();
        this.country = location.getCountry();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    public Long getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getZipCode() {
        return zipCode;
    }
    public String getCountry() {
        return country;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LocationDTO{" + "id=" + id + ", address='" + address + '\'' + ", city='" + city + '\'' + ", zipCode='" + zipCode + '\'' + ", country='" + country + '\'' + ", longitude='" + longitude + '\'' + ", latitude='" + latitude + '\'' + '}';
    }
}
