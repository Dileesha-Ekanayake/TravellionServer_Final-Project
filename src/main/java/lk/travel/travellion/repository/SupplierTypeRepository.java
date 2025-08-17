package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Suppliertype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierTypeRepository extends JpaRepository<Suppliertype, Integer> {
}
