package entities;

import javax.persistence.*;

@Entity
@Table(name = "BusinessAccounts")
public class BusinessAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessaccount_ID", nullable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isAdmin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CVR", nullable = false)
    private FoocleBusiness cvr;

    public BusinessAccount() {

    }
    public BusinessAccount(boolean isAdmin, Account account, FoocleBusiness cvr) {
        this.isAdmin = isAdmin;
        this.account = account;
        this.cvr = cvr;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
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

}