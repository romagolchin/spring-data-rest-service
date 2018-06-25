package service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ApplicationDto {
    @JsonProperty("CONTACT_ID")
    private long contactId;

    @JsonProperty("DT_CREATED")
    private LocalDateTime dateCreated;

    @JsonProperty("APPLICATION_ID")
    private long applicationId;

    @JsonProperty("PRODUCT_NAME")
    private String productName;

    public ApplicationDto(long contactId, LocalDateTime dateCreated, long applicationId, String productName) {
        this.contactId = contactId;
        this.dateCreated = dateCreated;
        this.applicationId = applicationId;
        this.productName = productName;
    }

    public long getContactId() {
        return contactId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public String getProductName() {
        return productName;
    }
}
