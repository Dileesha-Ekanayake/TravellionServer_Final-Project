package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Supplierstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierStatusRepository extends JpaRepository<Supplierstatus, Integer> {
}
