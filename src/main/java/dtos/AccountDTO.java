package dtos;

import entities.Location;

public class AccountDTO {

    private Long id;
    private String email;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String description;

    private Location location;


    public AccountDTO(Long id, String email, String phoneNumber, String firstname, String lastname, String description, Location location) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.location = location;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getNumber() {
        return phoneNumber;
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
    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "AccountDTO{" + "id=" + id + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", description='" + description + '\'' + ", location=" + location + '}';
    }
}
