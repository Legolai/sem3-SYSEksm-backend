package dtos;

import entities.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class FoocleScoutDTO {

    private Long scoutId;
//    private Set<ScoutRequestDTO> scoutRequests = new LinkedHashSet<>();

    private Long accountId;
    private String email;
    private String firstname;
    private String lastname;
    private String description;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String phoneNumber;
    private String areaCode;

    private Long locationId;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    public FoocleScoutDTO(Long scoutId, Long accountId, String email, String firstname, String lastname, String description, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String phoneNumber, String areaCode, Long locationId, String address, String city, String zipCode, String country) {
        this.scoutId = scoutId;
        this.accountId = accountId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.phoneNumber = phoneNumber;
        this.areaCode = areaCode;
        this.locationId = locationId;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
    public FoocleScoutDTO(FoocleScout foocleScout, Account account, Phone phone, Location location) {
        this.scoutId = foocleScout.getId();
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.firstname = account.getFirstname();
        this.lastname = account.getLastname();
        this.description = account.getDescription();
        this.password = account.getPassword();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.phoneNumber = phone.getId();
        this.areaCode = phone.getAreaCode();
        this.locationId = location.getId();
        this.address = location.getAddress();
        this.city = location.getCity();
        this.zipCode = location.getZipCode();
        this.country = location.getCountry();
    }
    public FoocleScoutDTO(FoocleScout foocleScout, Account account, Phone phone) {
        this.scoutId = foocleScout.getId();
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.firstname = account.getFirstname();
        this.lastname = account.getLastname();
        this.description = account.getDescription();
        this.password = account.getPassword();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.phoneNumber = phone.getId();
        this.areaCode = phone.getAreaCode();
    }
    public Long getScoutId() {
        return scoutId;
    }
    public Long getAccountId() {
        return accountId;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getDescription() {
        return description;
    }
    public String getPassword() {
        return password;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAreaCode() {
        return areaCode;
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

    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
