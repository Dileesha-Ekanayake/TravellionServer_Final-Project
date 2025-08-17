package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Tourcontract;
import lk.travel.travellion.uitl.numberService.projection.TourcontractRefNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface TourcontractRepository extends JpaRepository<Tourcontract, Integer>, JpaSpecificationExecutor<Tourcontract> {
    boolean existsByReference(@Size(max = 45) @NotNull String reference);

    boolean existsByReferenceAndIdNot(@Size(max = 45) @NotNull String reference, Integer id);

    TourcontractRefNo findTopByOrderByIdDesc();

    Optional<Tourcontract> findByReference(String reference);

    @Query("SELECT COALESCE(COUNT(t.id), 0) FROM Tourcontract t WHERE DATEDIFF(CURRENT_DATE, t.createdon) BETWEEN 0 AND 3")
    Long getAllNewlyCreatedTours();
}
