package service;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

public class QueryResult {
    private long contactId;

    private Date dateCreated;


    private long applicationId;

    private String productName;

    public QueryResult(long contactId, Date dateCreated, long applicationId, String productName) {
        this.contactId = contactId;
        this.dateCreated = dateCreated;
        this.applicationId = applicationId;
        this.productName = productName;
    }

    public long getContactId() {
        return contactId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public String getProductName() {
        return productName;
    }
}
