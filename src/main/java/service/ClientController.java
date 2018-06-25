package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    private ApplicationRepository repository;

    @Autowired
    public ClientController(ApplicationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/lastApplicationByContactId")
    public ApplicationDto handleLastApplicationByIdQuery(/* fixme */ @RequestParam(value = "contactId", defaultValue = "1") long contactId) {
        List<Application> applications = repository.findApplicationsByContactId(contactId);
        Application lastApplication = new Application();
        if (!applications.isEmpty()) {
            lastApplication = applications.get(0);
            for (Application application : applications) {
                if (application.getDateCreated().compareTo(lastApplication.getDateCreated()) > 0) {
                    lastApplication = application;
                }
            }
        }
        return new ApplicationDto(contactId, lastApplication.getDateCreated(), lastApplication.getId(), lastApplication.getProductName());
    }
}
