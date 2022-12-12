package dtos;

import entities.Notification;
import entities.ScoutRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link entities/ScoutRequest} entity
 */
public class MadeScoutRequestDto implements Serializable {
    private Long id;
    private String message;
    private String status;
    private Long spotmenuId;
    private String spotmenuDescription;
    private Long FooclespotId;
    private String BusinessName;

    public MadeScoutRequestDto(ScoutRequest scoutRequest) {
        this.id = scoutRequest.getId();
        this.message = scoutRequest.getMessage();
        this.status = scoutRequest.getStatus();
        this.spotmenuId = scoutRequest.getSpotmenu().getId();
        this.spotmenuDescription = scoutRequest.getSpotmenu().getDescription();
        this.FooclespotId = scoutRequest.getSpotmenu().getFooclespot().getId();
        this.BusinessName = scoutRequest.getSpotmenu().getFooclespot().getCvr().getName();
    }

    public static List<MadeScoutRequestDto> listToDTOs(List<ScoutRequest> scoutRequests) {
        return scoutRequests.stream().map(MadeScoutRequestDto::new).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSpotmenuId() {
        return spotmenuId;
    }

    public void setSpotmenuId(Long spotmenuId) {
        this.spotmenuId = spotmenuId;
    }

    public String getSpotmenuDescription() {
        return spotmenuDescription;
    }

    public void setSpotmenuDescription(String spotmenuDescription) {
        this.spotmenuDescription = spotmenuDescription;
    }

    public Long getFooclespotId() {
        return FooclespotId;
    }

    public void setFooclespotId(Long fooclespotId) {
        this.FooclespotId = fooclespotId;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        this.BusinessName = businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MadeScoutRequestDto entity = (MadeScoutRequestDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.message, entity.message) &&
                Objects.equals(this.status, entity.status) &&
                Objects.equals(this.spotmenuId, entity.spotmenuId) &&
                Objects.equals(this.spotmenuDescription, entity.spotmenuDescription) &&
                Objects.equals(this.FooclespotId, entity.FooclespotId) &&
                Objects.equals(this.BusinessName, entity.BusinessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, status, spotmenuId, spotmenuDescription, FooclespotId, BusinessName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "message = " + message + ", " +
                "status = " + status + ", " +
                "spotmenuId = " + spotmenuId + ", " +
                "spotmenuDescription = " + spotmenuDescription + ", " +
                "spotmenuFooclespotId = " + FooclespotId + ", " +
                "spotmenuFooclespotCvrName = " + BusinessName + ")";
    }
}