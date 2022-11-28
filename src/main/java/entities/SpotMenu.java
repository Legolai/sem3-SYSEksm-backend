package entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class SpotMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spotmenu_ID", nullable = false)
    private Long id;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "pictures")
    private String pictures;

    @Lob
    @Column(name = "food_prefences", nullable = false)
    private String foodPrefences;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "pickup_time_from", nullable = false)
    private Instant pickupTimeFrom;

    @Column(name = "pickup_time_to", nullable = false)
    private Instant pickupTimeTo;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fooclespots_ID", nullable = false)
    private FoocleSpot fooclespots;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getFoodPrefences() {
        return foodPrefences;
    }

    public void setFoodPrefences(String foodPrefences) {
        this.foodPrefences = foodPrefences;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getPickupTimeFrom() {
        return pickupTimeFrom;
    }

    public void setPickupTimeFrom(Instant pickupTimeFrom) {
        this.pickupTimeFrom = pickupTimeFrom;
    }

    public Instant getPickupTimeTo() {
        return pickupTimeTo;
    }

    public void setPickupTimeTo(Instant pickupTimeTo) {
        this.pickupTimeTo = pickupTimeTo;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FoocleSpot getFooclespots() {
        return fooclespots;
    }

    public void setFooclespots(FoocleSpot fooclespots) {
        this.fooclespots = fooclespots;
    }

}