package dtos;

import entities.ScoutRequest;
import entities.SpotMenu;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<ScoutRequestDTO> listToDTOs(List<ScoutRequest> scoutRequests) {
        return scoutRequests.stream().map(ScoutRequestDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getSpotmenuID() {
        return spotmenuID;
    }

    public Long getFooclescoutsID() {
        return fooclescoutsID;
    }
}
