package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "FoocleScouts")
public class FoocleScout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fooclescout_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "fooclescouts")
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "fooclescouts")
    private Set<ScoutRequest> scoutRequests = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<ScoutRequest> getScoutRequests() {
        return scoutRequests;
    }

    public void setScoutRequests(Set<ScoutRequest> scoutRequests) {
        this.scoutRequests = scoutRequests;
    }

}