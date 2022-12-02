package entities;

import javax.persistence.*;

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

    public Location() {
    }
    public Location(String address, String city, String zipCode, String country) {
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

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

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", address='" + address + '\'' + ", city='" + city + '\'' + ", zipCode='" + zipCode + '\'' + ", country='" + country + '\'' + '}';
    }
}