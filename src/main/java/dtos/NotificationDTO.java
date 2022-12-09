package dtos;

import entities.Account;
import entities.FoocleSpot;
import entities.Notification;
import entities.ScoutRequest;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationDTO {
    private Long id;
    private String message;
    private Long scoutrequestsId;

    public NotificationDTO(Long id, String message, Long scoutrequestsId) {
        this.id = id;
        this.message = message;
        this.scoutrequestsId = scoutrequestsId;
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.scoutrequestsId = notification.getScoutrequests().getId();
    }

    public static List<NotificationDTO> listToDTOs(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Long getScoutrequestsId() {
        return scoutrequestsId;
    }
}
