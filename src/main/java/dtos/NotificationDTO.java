package dtos;

import entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationDTO {
    private Long id;
    private String message;
    private String instructions;

    private StatusType status;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.instructions = notification.getInstructions();
        this.status = notification.getStatus();
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

    public String getInstructions() {
        return instructions;
    }

    public StatusType getStatus() {
        return status;
    }
}
