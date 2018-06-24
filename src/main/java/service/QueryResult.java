package service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class QueryResult {
    @JsonProperty("CONTACT_ID")
    private long contactId;

    @JsonProperty("DT_CREATED")
    private Date dateCreated;

    @JsonProperty("APPLICATION_ID")
    private long applicationId;

    @JsonProperty("PRODUCT_NAME")
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
