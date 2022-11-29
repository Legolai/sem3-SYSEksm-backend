package dtos;

import entities.Location;
import entities.Phone;

public class AccountDTO {

    private Long id;

    private String email;

    private String firstname;

    private String lastname;

    private String description;

    private Phone number;

    private Location location;

    public AccountDTO(Long id, String email, String firstname, String lastname, String description, Phone number, Location location) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.number = number;
        this.location = location;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setNumber(Phone number) {
        this.number = number;
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDescription() {
        return description;
    }

    public Phone getNumber() {
        return number;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", description='" + description + '\'' +
                ", number=" + number +
                ", location=" + location +
                '}';
    }
}
