package entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_ID", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "phoneNumber", nullable = false, length = 45)
    private String phoneNumber;

    @Column(name = "firstname", nullable = false, length = 45)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 45)
    private String lastname;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_ID")
    private Location location;

    public Account() {
    }
    public Account(String email, String phoneNumber, String firstname, String lastname, String password) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

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

    public boolean verifyPassword(String checkPassword) {
        return BCrypt.checkpw(checkPassword, this.password);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", description='" + description + '\'' + ", password='" + password + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", location=" + location + '}';
    }
}