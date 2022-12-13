package dtos;

import entities.ScoutRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScoutRequestMenuDTO {

    private Long id;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long fooclescoutsID;

    private Long spotMenuID;
    private String description;
    private String pictures;
    private String foodPreferences;
    private LocalDateTime pickupTimeFrom;
    private LocalDateTime pickupTimeTo;

    private long foocleSpotID;

    public ScoutRequestMenuDTO(ScoutRequest scoutRequest) {
        this.id = scoutRequest.getId();
        this.message = scoutRequest.getMessage();
        this.status = scoutRequest.getStatus();
        this.fooclescoutsID = scoutRequest.getFooclescout().getId();
        this.createdAt = scoutRequest.getCreatedAt();
        this.updatedAt = scoutRequest.getUpdatedAt();

        this.spotMenuID = scoutRequest.getSpotmenu().getId();
        this.description = scoutRequest.getSpotmenu().getDescription();
        this.pictures = scoutRequest.getSpotmenu().getPictures();
        this.foodPreferences = scoutRequest.getSpotmenu().getFoodPrefences();
        this.pickupTimeFrom = scoutRequest.getSpotmenu().getPickupTimeFrom();
        this.pickupTimeTo = scoutRequest.getSpotmenu().getPickupTimeTo();
        this.foocleSpotID = scoutRequest.getSpotmenu().getFooclespot().getId();
    }

    public static List<ScoutRequestMenuDTO> listToDTOs(List<ScoutRequest> scoutRequests) {
        return scoutRequests.stream().map(ScoutRequestMenuDTO::new).collect(Collectors.toList());
    }

}
