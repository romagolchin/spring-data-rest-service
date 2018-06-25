package service;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Service {
    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

    // populates in-memory DB. it is not necessary for tests because in tests data is also inserted
    @Profile("!test")
    @Bean
    public ApplicationRunner loadData(ApplicationRepository applicationRepository, ContactRepository contactRepository) {
        return (args) -> {
            int numberOfContacts = 10;
            List<Application> currentApplications = new ArrayList<>();
            for (int i = 0; i < numberOfContacts; i++) {
                Application application = new Application(LocalDateTime.now(), "product");
                Application otherApplication = new Application(LocalDateTime.now(), "product");
                currentApplications.add(application);
                currentApplications.add(otherApplication);
                for (Application a : currentApplications) {
                    applicationRepository.save(a);
                }
//                contactRepository.save(new Contact(currentApplications));
                currentApplications.clear();
            }
        };
    }
}
