package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Supplier;
import lk.travel.travellion.uitl.numberService.projection.SupplierBrNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

    boolean existsByBrno(@Size(max = 10) String brno);

    Optional<Supplier> findByBrno(@Size(max = 10) String brno);

    boolean existsByBrnoAndIdNot(@Size(max = 10) String brno, Integer id);

    List<SupplierBrNo> findAllByOrderByBrnoDesc();

    boolean existsByBankAccount(@Size(max = 45) String bankAccount);

    boolean existsByBankAccountAndIdNot(@Size(max = 45) String bankAccount, Integer id);

    boolean existsByMobile(@Size(max = 10) String mobile);

    boolean existsByMobileAndIdNot(@Size(max = 10) String mobile, Integer id);

    boolean existsByLand(@Size(max = 10)  String land);

    boolean existsByLandAndIdNot(@Size(max = 10) String land, Integer id);

    boolean existsByEmail(@Size(max = 100) String email);

    boolean existsByEmailAndIdNot(@Size(max = 100) String email, Integer id);

    List<Supplier> findByIsActiveTrueAndSupplierstatus_Id(Integer supplierstatusId);

    List<Supplier> findByIsActiveTrueAndSupplierstatus_IdAndSuppliertype_Id(Integer supplierstatusId, Integer suppliertypeId);
}
