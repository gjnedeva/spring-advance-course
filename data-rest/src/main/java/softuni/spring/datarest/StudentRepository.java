package softuni.spring.datarest;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//RepositoryRestResource it will be exposed by Spring like Rest API
@RepositoryRestResource(collectionResourceRel = "students", path = "students")
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByFirstName(@Param("first_name") String firstName);
}
