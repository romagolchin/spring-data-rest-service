package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClientController {
    private ApplicationRepository repository;

    @Autowired
    public ClientController(ApplicationRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(path = "/contact/{id}/lastApplication")
    public Optional<ApplicationDto> handleLastApplicationByIdQuery(@PathVariable long id) {
        Optional<Application> lastApplication = repository.findApplicationsByContactId(id,
                PageRequest.of(0, 1)).stream().findFirst();
        return lastApplication.map(application ->
                new ApplicationDto(id, application.getDateCreated(), application.getId(), application.getProductName()));
    }
}
