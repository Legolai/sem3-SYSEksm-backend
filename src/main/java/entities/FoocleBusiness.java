package entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class FoocleBusiness {
    @Id
    @Column(name = "CVR", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "businessEmail", nullable = false, length = 45)
    private String businessEmail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "businessPhone", nullable = false)
    private Phone businessPhone;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_ID", nullable = false)
    private Location location;

    public FoocleBusiness() {
    }
    public FoocleBusiness(String id, String name, String businessEmail, String description, Phone businessPhone, Location location) {
        this.id = id;
        this.name = name;
        this.businessEmail = businessEmail;
        this.description = description;
        this.businessPhone = businessPhone;
        this.location = location;
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime currentTime = LocalDateTime.now();
        int nano = currentTime.getNano();
        this.createdAt = currentTime.minusNanos(nano);
        this.updatedAt = currentTime.minusNanos(nano);
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        int nano = currentTime.getNano();
        this.updatedAt = currentTime.minusNanos(nano);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBusinessEmail() {
        return businessEmail;
    }
    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }
    public Phone getBusinessPhone() {
        return businessPhone;
    }
    public void setBusinessPhone(Phone businessPhone) {
        this.businessPhone = businessPhone;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

}