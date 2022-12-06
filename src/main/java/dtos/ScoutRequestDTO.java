package dtos;

import entities.ScoutRequest;

import java.time.Instant;
import java.time.LocalDateTime;

public class ScoutRequestDTO {

    private Long id;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long spotmenuID;
    private Long fooclescoutsID;

    public ScoutRequestDTO(ScoutRequest scoutRequest) {
        this.id = scoutRequest.getId();
        this.message = scoutRequest.getMessage();
        this.status = scoutRequest.getStatus();
        this.spotmenuID = scoutRequest.getSpotmenu().getId();
        this.fooclescoutsID = scoutRequest.getFooclescout().getId();
        this.createdAt = scoutRequest.getCreatedAt();
        this.updatedAt = scoutRequest.getUpdatedAt();
    }
}
