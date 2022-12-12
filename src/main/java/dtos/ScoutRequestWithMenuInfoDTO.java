package dtos;

import entities.ScoutRequest;
import entities.SpotMenu;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScoutRequestWithMenuInfoDTO {

    private Long id;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long spotmenuID;
    private String description;
    private LocalDateTime pickupTimeFrom;
    private LocalDateTime pickupTimeTo;

    private Long fooclescoutsID;

    public ScoutRequestWithMenuInfoDTO(ScoutRequest scoutRequest) {
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

}
