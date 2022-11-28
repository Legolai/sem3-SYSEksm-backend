package dtos;

import entities.FoocleBusiness;
import entities.Location;

import java.time.Instant;
import java.util.LinkedHashSet;

public class FoocleBusinessDTO {

    private String cvr; //id
    private String name;
    private String email;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Location location;
    private LinkedHashSet<BusinessAccountDTO> businessAccounts;

    public FoocleBusinessDTO(String cvr, String name, String email, String description, Instant createdAt, Instant updatedAt, Location location, LinkedHashSet<BusinessAccountDTO> businessAccounts) {
        this.cvr = cvr;
        this.name = name;
        this.email = email;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.location = location;
        this.businessAccounts = businessAccounts;
    }
    public FoocleBusinessDTO(String cvr, String name, String email, String description, Location location) {
        this.cvr = cvr;
        this.name = name;
        this.email = email;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.location = location;
    }
    public FoocleBusinessDTO(FoocleBusiness business) {
        this.cvr = business.getId();
        this.name = business.getName();
        this.email = business.getEmail();
        this.description = business.getDescription();
        this.createdAt = business.getCreatedAt();
        this.updatedAt = business.getUpdatedAt();
        this.location = business.getLocation();
    }

    public String getCvr() {
        return cvr;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getDescription() {
        return description;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public Location getLocation() {
        return location;
    }
    public LinkedHashSet<BusinessAccountDTO> getBusinessAccounts() {
        return businessAccounts;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void setBusinessAccounts(LinkedHashSet<BusinessAccountDTO> businessAccounts) {
        this.businessAccounts = businessAccounts;
    }

    @Override
    public String toString() {
        return "FoocleBusinessDTO{" + "cvr='" + cvr + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", description='" + description + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", location=" + location + ", businessAccounts=" + businessAccounts + '}';
    }
}
