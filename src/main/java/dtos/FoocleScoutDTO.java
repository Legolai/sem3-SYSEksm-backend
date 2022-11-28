package dtos;

import entities.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

public class FoocleScoutDTO {

    private Long scoutId;
    private Set<ScoutRequestDTO> scoutRequests = new LinkedHashSet<>();

    private Long accountId;
    private String email;
    private String firstname;
    private String lastname;
    private String description;
    private String password;
    private Instant createdAt;
    private Instant updatedAt;

    private String phoneNumber;
    private String areaCode;

    private Long locationId;
    private String address;
    private String city;
    private String zipCode;
    private String country;


}
