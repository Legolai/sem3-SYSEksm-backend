package dtos;

import entities.FoocleScout;
import entities.SpotMenu;

import javax.persistence.*;
import java.time.Instant;

public class ScoutRequestDTO {

    private Long id;
    private String message;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

    private Long spotmenuID;
    private Long fooclescoutsID;

}
