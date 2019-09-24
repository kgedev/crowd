package ru.skilup.crowd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skilup.crowd.domain.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    @Query("from Employee e " +
            "where concat(e.lastName, ' ', e.firstName, ' ', e.patronymic)" +
            "like concat('%',:name,'%')")
    List<Employee> findByName(@Param("name") String name);

    Optional<Employee> findById(UUID id);
}
