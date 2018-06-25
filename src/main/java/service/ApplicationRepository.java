package service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationRepository extends CrudRepository<Application, Long> {

    @Query("select a from Contact c, in(c.applications) a where c.id = :contactId order by a.dateCreated desc")
//    @Query("select a from (select c.applications from Contact c where c.id = :contactId) b order by a.dateCreated desc")
    List<Application> findApplicationsByContactId(@Param("contactId") long contactId, Pageable pageable);
}
