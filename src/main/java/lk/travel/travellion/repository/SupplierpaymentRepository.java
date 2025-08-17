package lk.travel.travellion.repository;

import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentInfoDTO;
import lk.travel.travellion.entity.Supplierpayment;
import lk.travel.travellion.uitl.numberService.projection.CustomerpaymentCode;
import lk.travel.travellion.uitl.numberService.projection.SupplierpaymentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierpaymentRepository extends JpaRepository<Supplierpayment, Integer>, JpaSpecificationExecutor<Supplierpayment> {

    SupplierpaymentCode findTopBySupplierIdOrderByIdDesc(Integer supplierId);

    boolean existsBySupplierBrno(String supplierBrno);

    boolean existsByCode(String code);

    @Query("SELECT COALESCE(SUM(cp.paidamount), 0) FROM Supplierpayment cp WHERE cp.supplier.brno = :brno")
    BigDecimal sumAmountBySupplierBrno(@Param("brno") String brno);
}
