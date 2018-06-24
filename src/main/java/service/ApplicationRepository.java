package service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {

    @Query("select a from Contact c, in(c.applications) a where c.id = :contactId order by a.dateCreated desc")
    List<Application> sortedApplications(@Param("contactId") long contactId);

}
