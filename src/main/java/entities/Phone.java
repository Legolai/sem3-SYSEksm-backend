package entities;

import javax.persistence.*;

@Entity
public class Phone {
    @Id
    @Column(name = "number", nullable = false, length = 45)
    private String id;

    @Column(name = "areaCode", nullable = false, length = 45)
    private String areaCode;

    public Phone() {
    }
    public Phone(String id, String areaCode) {
        this.id = id;
        this.areaCode = areaCode;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return "Phone{" + "id='" + id + '\'' + ", areaCode='" + areaCode + '\'' + '}';
    }
}