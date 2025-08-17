package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Employeestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<Employeestatus, Integer> {
}
