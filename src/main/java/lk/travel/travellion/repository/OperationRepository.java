package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    List<Operation> findByModule_Id(Integer moduleId);
}
