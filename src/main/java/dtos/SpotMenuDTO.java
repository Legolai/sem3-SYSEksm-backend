package dtos;

import entities.SpotMenu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SpotMenuDTO {

    private Long id;
    private String description;
    private String pictures;
    private String foodPreferences;

    private LocalDateTime pickupTimeFrom;
    private LocalDateTime pickupTimeTo;

    private long foocleSpotID;


    public SpotMenuDTO(String description, String pictures, String foodPreferences, LocalDateTime pickupTimeFrom, LocalDateTime pickupTimeTo, long foocleSpotID) {
        this.description = description;
        this.pictures = pictures;
        this.foodPreferences = foodPreferences;
        this.pickupTimeFrom = pickupTimeFrom;
        this.pickupTimeTo = pickupTimeTo;
        this.foocleSpotID = foocleSpotID;
    }
    public SpotMenuDTO(SpotMenu spotMenu) {
        this.description = spotMenu.getDescription();
        this.pictures = spotMenu.getPictures();
        this.foodPreferences = spotMenu.getFoodPrefences();
        this.pickupTimeFrom = spotMenu.getPickupTimeFrom();
        this.pickupTimeTo = spotMenu.getPickupTimeTo();
        this.foocleSpotID = spotMenu.getFooclespots().getId();
    }

    public static List<SpotMenuDTO> listToDTOs(List<SpotMenu> spotMenus) {
        return spotMenus.stream().map(SpotMenuDTO::new).collect(Collectors.toList());
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
    public String getFoodPreferences() {
        return foodPreferences;
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
    public void setFoodPreferences(String foodPreferences) {
        this.foodPreferences = foodPreferences;
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
