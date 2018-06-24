package service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    @Column(nullable = false)
    private List<Application> applications;

    public Contact() {
        applications = new ArrayList<>();
    }

    public Contact(List<Application> applications) {
        this.applications = applications;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", applications=" + applications +
                '}';
    }
}
