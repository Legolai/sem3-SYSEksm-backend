package entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "FoocleSpots")
public class FoocleSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fooclespot_ID", nullable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "businessaccounts_ID", nullable = false)
    private BusinessAccount businessaccounts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CVR", nullable = false)
    private FoocleBusiness cvr;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_ID", nullable = false)
    private Location location;

    public FoocleSpot() {

    }
    public FoocleSpot(BusinessAccount businessaccounts, FoocleBusiness cvr, Location location) {
        this.businessaccounts = businessaccounts;
        this.cvr = cvr;
        this.location = location;
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime currentTime = LocalDateTime.now();
        int nano = currentTime.getNano();
        this.createdAt = currentTime.minusNanos(nano);
        this.updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        int nano = currentTime.getNano();
        this.updatedAt = currentTime.minusNanos(nano);
    }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public BusinessAccount getBusinessaccounts() {
        return businessaccounts;
    }
    public void setBusinessaccounts(BusinessAccount businessaccounts) {
        this.businessaccounts = businessaccounts;
    }
    public FoocleBusiness getCvr() {
        return cvr;
    }
    public void setCvr(FoocleBusiness cvr) {
        this.cvr = cvr;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

}