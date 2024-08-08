package Department.Department.of.Employee.Repository;

import Department.Department.of.Employee.DTO.EmployeeDTO;
import Department.Department.of.Employee.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity , Long> {

}
