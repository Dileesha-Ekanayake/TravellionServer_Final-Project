package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Transfercontract;
import lk.travel.travellion.uitl.numberService.projection.TransfercontractRefNo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfercontractRepository extends JpaRepository<Transfercontract, Integer>, JpaSpecificationExecutor<Transfercontract> {
    boolean existsByReferenceAndIdNot(@Size(max = 45) @NotNull String reference, Integer id);

    boolean existsByReference(@Size(max = 45) @NotNull String reference);

    TransfercontractRefNo findTopBySupplierIdOrderByIdDesc(Integer supplierId);
}
