package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Employeetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTypeRepository extends JpaRepository<Employeetype, Integer> {
}
