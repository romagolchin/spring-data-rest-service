package service;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication
public class Service {
    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

    // populates in-memory DB. it is not necessary for tests because in tests data is also inserted
    @Profile("!test")
    @Bean
    public ApplicationRunner loadData(ApplicationRepository applicationRepository) {
        return (args) -> {
            int numberOfContacts = 10;
            for (int i = 0; i < numberOfContacts; i++) {
                applicationRepository.save(new Application(i + 1, LocalDate.now(), "product"));
                applicationRepository.save(new Application(i + 1, LocalDate.now(), "product"));
            }
        };
    }
}
