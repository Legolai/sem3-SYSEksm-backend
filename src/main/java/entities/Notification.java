package entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_ID", nullable = false)
    private Long id;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scoutrequests_ID", nullable = false)
    private ScoutRequest scoutrequests;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ScoutRequest getScoutrequests() {
        return scoutrequests;
    }

    public void setScoutrequests(ScoutRequest scoutrequests) {
        this.scoutrequests = scoutrequests;
    }

}