package service;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    private String productName;

    public Application() {
    }

    public Application(Date dateCreated, String productName) {
        this.dateCreated = dateCreated;
        this.productName = productName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", productName='" + productName + '\'' +
                '}';
    }
}
