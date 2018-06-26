package service;

import org.springframework.data.repository.CrudRepository;


public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Application findFirstByContactIdOrderByDateCreatedDesc(long id);
}
