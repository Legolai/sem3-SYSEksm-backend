package entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "FoocleSpots")
public class FoocleSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fooclespot_ID", nullable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "businessaccounts_ID", nullable = false)
    private BusinessAccount businessaccounts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CVR", nullable = false)
    private FoocleBusiness cvr;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_ID", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "fooclespots")
    private Set<SpotMenu> spotMenus = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
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

    public Set<SpotMenu> getSpotMenus() {
        return spotMenus;
    }

    public void setSpotMenus(Set<SpotMenu> spotMenus) {
        this.spotMenus = spotMenus;
    }

}