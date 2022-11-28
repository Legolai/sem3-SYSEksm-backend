package dtos;

import entities.*;

import java.time.Instant;
import java.time.LocalDateTime;

public class BusinessAccountDTO {

    private Long businessAccountId;
    private boolean isAdmin;
    private String cvr;

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

    public BusinessAccountDTO(Long businessAccountId, boolean isAdmin, String cvr, Long accountId, String email, String firstname, String lastname, String description, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String phoneNumber, String areaCode, Long locationId, String address, String city, String zipCode, String country) {
        this.businessAccountId = businessAccountId;
        this.isAdmin = isAdmin;
        this.cvr = cvr;
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
    public BusinessAccountDTO(BusinessAccount businessAccount, Account account, Phone phone, Location location) {
        this.businessAccountId = businessAccount.getId();
        this.isAdmin = businessAccount.getIsAdmin();
        this.cvr = businessAccount.getCvr().getId();
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
    public BusinessAccountDTO(BusinessAccount businessAccount, Account account, Phone phone) {
        this.businessAccountId = businessAccount.getId();
        this.isAdmin = businessAccount.getIsAdmin();
        this.cvr = businessAccount.getCvr().getId();
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


    public Long getBusinessAccountId() {
        return businessAccountId;
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public String getCvr() {
        return cvr;
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

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setCvr(String cvr) {
        this.cvr = cvr;
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

    @Override
    public String toString() {
        return "FoocleBusinessAccountDTO{" + "businessAccountId=" + businessAccountId + ", isAdmin=" + isAdmin + ", cvr='" + cvr + '\'' + ", accountId=" + accountId + ", email='" + email + '\'' + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", description='" + description + '\'' + ", password='" + password + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", phoneNumber='" + phoneNumber + '\'' + ", areaCode='" + areaCode + '\'' + ", locationId=" + locationId + ", address='" + address + '\'' + ", city='" + city + '\'' + ", zipCode='" + zipCode + '\'' + ", country='" + country + '\'' + '}';
    }
}
