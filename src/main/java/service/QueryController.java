package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {
    private ApplicationRepository repository;

    @Autowired
    public QueryController(ApplicationRepository repository) {
        this.repository = repository;
    }

    // if contact with given id does not exist or does not have any applications
    // 0 is returned for APPLICATION_ID and nulls for both DATE_CREATED and PRODUCT_NAME
    @RequestMapping(path = "/lastApplicationByContactId")
    public QueryResult handleLastApplicationByIdQuery(@RequestParam(value = "contactId", defaultValue = "1") long contactId) {
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
        return new QueryResult(contactId, lastApplication.getDateCreated(), lastApplication.getId(), lastApplication.getProductName());
    }
}
