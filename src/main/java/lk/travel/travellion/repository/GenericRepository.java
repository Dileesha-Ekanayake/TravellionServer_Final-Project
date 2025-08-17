package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Generic;
import lk.travel.travellion.uitl.numberService.projection.GenericRefNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericRepository extends JpaRepository<Generic, Integer>, JpaSpecificationExecutor<Generic> {
  boolean existsByReference(@Size(max = 45) @NotNull String reference);

  boolean existsByReferenceAndIdNot(@Size(max = 45) @NotNull String reference, Integer id);

  GenericRefNo findTopByOrderByIdDesc();
}
