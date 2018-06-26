package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private ApplicationRepository repository;

    @Autowired
    public ClientController(ApplicationRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(path = "/client/{id}/lastApplication")
    public Application findLastApplicationById(@PathVariable long id) {
        return repository.findFirstByContactIdOrderByDateCreatedDesc(id);
    }
}
