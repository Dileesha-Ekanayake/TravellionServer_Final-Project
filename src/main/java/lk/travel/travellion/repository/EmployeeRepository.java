package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Employee;
import lk.travel.travellion.uitl.numberService.projection.EmployeeNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    List<EmployeeNumbers> findAllByOrderByNumberDesc();

    Optional<Employee> findByNumber(@Size(max = 6) String number);

    Boolean existsByNumber(@Size(max = 6) String number);

    Boolean existsByNic(@Size(max = 12) String nic);

    Boolean existsByMobile(@Size(max = 10) String mobile);

    Boolean existsByEmail(@Size(max = 45) String email);

    boolean existsByNumberAndIdNot(@Size(max = 6) String number, Integer currentEmployeeId);

    boolean existsByNicAndIdNot(@Size(max = 12) String nic, Integer currentEmployeeId);

    boolean existsByMobileAndIdNot(@Size(max = 10) String mobile, Integer currentEmployeeId);

    boolean existsByEmailAndIdNot(@Size(max = 45) String email, Integer currentEmployeeId);
}
