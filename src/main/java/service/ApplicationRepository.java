package service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationRepository extends CrudRepository<Application, Long> {

    @Query("select c.applications from Contact c where c.id = :contactId")
    List<Application> findApplicationsByContactId(@Param("contactId") long contactId);
}
