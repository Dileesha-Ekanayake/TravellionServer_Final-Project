package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Customer;
import lk.travel.travellion.uitl.numberService.projection.CustomerCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {


    Optional<Customer> findByCode(@Size(max = 11) String code);

    CustomerCode findTopByOrderByIdDesc();

    long countByCreatedonBetween(Timestamp createdonAfter, Timestamp createdonBefore);
}
