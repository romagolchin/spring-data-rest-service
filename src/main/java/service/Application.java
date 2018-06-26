package service;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("APPLICATION_ID")
    private long id;

    @JsonProperty("CONTACT_ID")
    
    private long contactId;

    @Column(nullable = false)
    @JsonProperty("DT_CREATED")
    
    private LocalDate dateCreated;

    @Column(nullable = false)
    @JsonProperty("PRODUCT_NAME")
    
    private String productName;

    public Application(long contactId, LocalDate dateCreated, String productName) {
        this.contactId = contactId;
        this.dateCreated = dateCreated;
        this.productName = productName;
    }

    public Application() {
    }

    public long getId() {
        return id;
    }

    public long getContactId() {
        return contactId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public String getProductName() {
        return productName;
    }
}
