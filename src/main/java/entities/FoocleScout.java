package entities;

import javax.persistence.*;

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

    public FoocleScout() {

    }
    public FoocleScout(Account account) {
        this.account = account;
    }


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

}