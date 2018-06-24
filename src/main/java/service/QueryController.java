package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {
    @Autowired
    private ApplicationRepository repository;

    @RequestMapping(path = "/lastApplication")
    public QueryResult queryResult(@RequestParam(value = "contactId", defaultValue = "1") long contactId) {
        List<Application> applications = repository.sortedApplications(contactId);
        Application lastApplication = new Application();
        if (!applications.isEmpty()) {
            lastApplication = applications.get(0);
        }
        return new QueryResult(contactId, lastApplication.getDateCreated(), lastApplication.getId(), lastApplication.getProductName());
    }
}
