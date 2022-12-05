package dtos;

import entities.*;

import java.time.LocalDateTime;
import java.util.List;

public class FoocleSpotDTO {

    private Long spotID;
    private long businessAccountID;
    private String cvr;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long locationId;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    public FoocleSpotDTO(Long spotID, long businessAccountID, String cvr, LocalDateTime createdAt, LocalDateTime updatedAt, Long locationId, String address, String city, String zipCode, String country) {
        this.spotID = spotID;
        this.businessAccountID = businessAccountID;
        this.cvr = cvr;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.locationId = locationId;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
    public FoocleSpotDTO(FoocleSpot foocleSpot) {
        this.spotID = foocleSpot.getId();
        this.businessAccountID = foocleSpot.getBusinessaccounts().getId();
        this.cvr = foocleSpot.getCvr().getId();
        this.createdAt = foocleSpot.getCreatedAt();
        this.updatedAt = foocleSpot.getUpdatedAt();
        this.locationId = foocleSpot.getLocation().getId();
        this.address = foocleSpot.getLocation().getAddress();
        this.city = foocleSpot.getLocation().getCity();
        this.zipCode = foocleSpot.getLocation().getZipCode();
        this.country = foocleSpot.getLocation().getCountry();
    }

    public Long getSpotID() {
        return spotID;
    }
    public long getBusinessAccountID() {
        return businessAccountID;
    }
    public String getCvr() {
        return cvr;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public Long getLocationId() {
        return locationId;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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
}
