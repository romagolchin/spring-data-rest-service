package service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("APPLICATION_ID")
    @Getter
    private long id;

    @JsonProperty("CONTACT_ID")
    @Getter
    private long contactId;

    @Column(nullable = false)
    @JsonProperty("DT_CREATED")
    @Getter
    private LocalDate dateCreated;

    @Column(nullable = false)
    @JsonProperty("PRODUCT_NAME")
    @Getter
    private String productName;

    public Application(long contactId, LocalDate dateCreated, String productName) {
        this.contactId = contactId;
        this.dateCreated = dateCreated;
        this.productName = productName;
    }
}
