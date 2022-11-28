package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "BusinessAccounts")
public class BusinessAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessaccount_ID", nullable = false)
    private Long id;

    @Column(name = "isAdmin", nullable = false)
    private Byte isAdmin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CVR", nullable = false)
    private FoocleBusiness cvr;

    @OneToMany(mappedBy = "businessaccounts")
    private Set<FoocleSpot> foocleSpots = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public FoocleBusiness getCvr() {
        return cvr;
    }

    public void setCvr(FoocleBusiness cvr) {
        this.cvr = cvr;
    }

    public Set<FoocleSpot> getFoocleSpots() {
        return foocleSpots;
    }

    public void setFoocleSpots(Set<FoocleSpot> foocleSpots) {
        this.foocleSpots = foocleSpots;
    }

}