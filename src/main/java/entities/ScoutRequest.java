package entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "ScoutRequests")
public class ScoutRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scoutrequest_ID", nullable = false)
    private Long id;

    @Lob
    @Column(name = "message")
    private String message;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spotmenu_ID", nullable = false)
    private SpotMenu spotmenu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fooclescouts_ID", nullable = false)
    private FoocleScout fooclescout;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime currentTime = LocalDateTime.now();
        int nano = currentTime.getNano();
        this.createdAt = currentTime.minusNanos(nano);
        this.updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        int nano = currentTime.getNano();
        this.updatedAt = currentTime.minusNanos(nano);
    }

    public ScoutRequest(String message, String status, SpotMenu spotmenu, FoocleScout fooclescout) {
        this.message = message;
        this.status = status;
        this.spotmenu = spotmenu;
        this.fooclescout = fooclescout;
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

    public SpotMenu getSpotmenu() {
        return spotmenu;
    }

    public void setSpotmenu(SpotMenu spotmenu) {
        this.spotmenu = spotmenu;
    }

    public FoocleScout getFooclescout() {
        return fooclescout;
    }

    public void setFooclescout(FoocleScout fooclescout) {
        this.fooclescout = fooclescout;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
