package entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    @Column(name = "pickup_time_from", nullable = false)
    private LocalDateTime pickupTimeFrom;

    @Column(name = "pickup_time_to", nullable = false)
    private LocalDateTime pickupTimeTo;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fooclespots_ID", nullable = false)
    private FoocleSpot fooclespot;

    public SpotMenu() {}
    public SpotMenu(String description, String pictures, String foodPrefences, LocalDateTime pickupTimeFrom, LocalDateTime pickupTimeTo, FoocleSpot fooclespot) {
        this.description = description;
        this.pictures = pictures;
        this.foodPrefences = foodPrefences;
        this.pickupTimeFrom = pickupTimeFrom;
        this.pickupTimeTo = pickupTimeTo;
        this.fooclespot = fooclespot;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getPickupTimeFrom() {
        return pickupTimeFrom;
    }
    public void setPickupTimeFrom(LocalDateTime pickupTimeFrom) {
        this.pickupTimeFrom = pickupTimeFrom;
    }
    public LocalDateTime getPickupTimeTo() {
        return pickupTimeTo;
    }
    public void setPickupTimeTo(LocalDateTime pickupTimeTo) {
        this.pickupTimeTo = pickupTimeTo;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public FoocleSpot getFooclespot() {
        return fooclespot;
    }
    public void setFooclespot(FoocleSpot fooclespot) {
        this.fooclespot = fooclespot;
    }

}
