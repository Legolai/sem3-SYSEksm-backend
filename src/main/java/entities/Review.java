package entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviews_ID", nullable = false)
    private Long id;

    @Column(name = "rating", nullable = false, precision = 10)
    private BigDecimal rating;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fooclescouts_ID", nullable = false)
    private FoocleScout fooclescouts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CVR", nullable = false)
    private FoocleBusiness cvr;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_ID", nullable = false)
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoocleScout getFooclescouts() {
        return fooclescouts;
    }

    public void setFooclescouts(FoocleScout fooclescouts) {
        this.fooclescouts = fooclescouts;
    }

    public FoocleBusiness getCvr() {
        return cvr;
    }

    public void setCvr(FoocleBusiness cvr) {
        this.cvr = cvr;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}