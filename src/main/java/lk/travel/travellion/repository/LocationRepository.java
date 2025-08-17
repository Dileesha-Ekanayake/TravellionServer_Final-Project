package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Location;
import lk.travel.travellion.uitl.numberService.projection.LocationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>, JpaSpecificationExecutor<Location> {
  boolean existsByCode(@Size(max = 6) String code);

  boolean existsByCodeAndIdNot(@Size(max = 6) String code, Integer id);

  LocationCode findTopByCodeLikeOrderByIdDesc(@Size(max = 6) String code);
}
