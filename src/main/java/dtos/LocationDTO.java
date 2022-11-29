package dtos;

import entities.Location;

import javax.persistence.Column;
import javax.persistence.Id;

public class LocationDTO {

    private Long id;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    public LocationDTO(Long id, String address, String city, String zipCode, String country) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
    public LocationDTO(Location location) {
        this.id = location.getId();
        this.address = location.getAddress();
        this.city = location.getCity();
        this.zipCode = location.getZipCode();
        this.country = location.getCountry();
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

    @Override
    public String toString() {
        return "LocationDTO{" + "id=" + id + ", address='" + address + '\'' + ", city='" + city + '\'' + ", zipCode='" + zipCode + '\'' + ", country='" + country + '\'' + '}';
    }
}
