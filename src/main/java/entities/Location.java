package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_ID", nullable = false)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zipCode", nullable = false, length = 45)
    private String zipCode;

    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @OneToMany(mappedBy = "location")
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<FoocleSpot> foocleSpots = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<Account> accounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<FoocleBusiness> foocleBusinesses = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<FoocleSpot> getFoocleSpots() {
        return foocleSpots;
    }

    public void setFoocleSpots(Set<FoocleSpot> foocleSpots) {
        this.foocleSpots = foocleSpots;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<FoocleBusiness> getFoocleBusinesses() {
        return foocleBusinesses;
    }

    public void setFoocleBusinesses(Set<FoocleBusiness> foocleBusinesses) {
        this.foocleBusinesses = foocleBusinesses;
    }

}