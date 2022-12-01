package dtos;

import entities.FoocleSpot;
import entities.SpotMenu;

import javax.persistence.*;
import java.time.LocalDateTime;

public class SpotMenuDTO {

    private Long id;
    private String description;
    private String pictures;
    private String foodPrefences;

    private LocalDateTime pickupTimeFrom;
    private LocalDateTime pickupTimeTo;

    private long foocleSpotID;


    public SpotMenuDTO(String description, String pictures, String foodPrefences, LocalDateTime pickupTimeFrom, LocalDateTime pickupTimeTo, long foocleSpotID) {
        this.description = description;
        this.pictures = pictures;
        this.foodPrefences = foodPrefences;
        this.pickupTimeFrom = pickupTimeFrom;
        this.pickupTimeTo = pickupTimeTo;
        this.foocleSpotID = foocleSpotID;
    }
    public SpotMenuDTO(SpotMenu spotMenu) {
        this.description = spotMenu.getDescription();
        this.pictures = spotMenu.getPictures();
        this.foodPrefences = spotMenu.getFoodPrefences();
        this.pickupTimeFrom = spotMenu.getPickupTimeFrom();
        this.pickupTimeTo = spotMenu.getPickupTimeTo();
        this.foocleSpotID = spotMenu.getFooclespots().getId();
    }

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getPictures() {
        return pictures;
    }
    public String getFoodPrefences() {
        return foodPrefences;
    }
    public LocalDateTime getPickupTimeFrom() {
        return pickupTimeFrom;
    }
    public LocalDateTime getPickupTimeTo() {
        return pickupTimeTo;
    }
    public long getFoocleSpotID() {
        return foocleSpotID;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
    public void setFoodPrefences(String foodPrefences) {
        this.foodPrefences = foodPrefences;
    }
    public void setPickupTimeFrom(LocalDateTime pickupTimeFrom) {
        this.pickupTimeFrom = pickupTimeFrom;
    }
    public void setPickupTimeTo(LocalDateTime pickupTimeTo) {
        this.pickupTimeTo = pickupTimeTo;
    }
    public void setFoocleSpotID(long foocleSpotID) {
        this.foocleSpotID = foocleSpotID;
    }

}
