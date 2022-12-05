package dtos;

import entities.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FoocleSpotAvailabeDTO {

    private Long id;

    private Long contact_id;

    private String email;

    private String phoneNumber;

    private String firstname;

    private String lastname;

    private String cvr;

    private String businessName;

    private Location location;


    public FoocleSpotAvailabeDTO(FoocleSpot foocleSpot) {
        this.id = foocleSpot.getId();
        this.contact_id = foocleSpot.getBusinessaccounts().getId();
        this.email = foocleSpot.getBusinessaccounts().getAccount().getEmail();
        this.phoneNumber = foocleSpot.getBusinessaccounts().getAccount().getPhoneNumber();
        this.firstname = foocleSpot.getBusinessaccounts().getAccount().getFirstname();
        this.lastname = foocleSpot.getBusinessaccounts().getAccount().getLastname();
        this.cvr = foocleSpot.getCvr().getId();
        this.businessName = foocleSpot.getCvr().getName();
        this.location = foocleSpot.getLocation();
    }

    public static List<FoocleSpotAvailabeDTO> listToDTOs(List<FoocleSpot> foocleSpotList) {
        return foocleSpotList.stream().map(FoocleSpotAvailabeDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
