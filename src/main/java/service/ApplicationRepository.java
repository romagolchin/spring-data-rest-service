package service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationRepository extends CrudRepository<Application, Long> {

    @Query("select a from Contact c, in(c.applications) a where c.id = :contactId")
    List<Application> findApplicationsByContactId(@Param("contactId") long contactId);
}
