package service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Service {
    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

    // populates in-memory DB. it is not necessary for tests because in tests data is also inserted
    @Profile("!test")
    @Bean
    public InitializingBean loadData(ApplicationRepository applicationRepository, ContactRepository contactRepository) {
        return () -> {
            Random random = new Random();
            int numberOfContacts = 10;
            List<Application> currentApplications = new ArrayList<>();
            long date = System.currentTimeMillis();
            for (int i = 0; i < numberOfContacts; i++) {
                Application application = new Application(new Date(date + random.nextInt()), "product");
                Application otherApplication = new Application(new Date(date + random.nextInt()), "product");
                currentApplications.add(application);
                currentApplications.add(otherApplication);
                for (Application a : currentApplications) {
                    applicationRepository.save(a);
                }
                contactRepository.save(new Contact(currentApplications));
                currentApplications.clear();
            }
        };
    }
}
