package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.City;
import lk.travel.travellion.uitl.numberService.projection.CityCode;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {
    Optional<City> findByCode(@NotNull @Size(max = 18) @Unique String code);

    boolean existsByCode(@NotNull @Size(max = 18) @Unique String code);

    boolean existsByCodeAndIdNot(@NotNull @Size(max = 18) @Unique String code, Integer id);

//    @Query("SELECT c FROM City c WHERE c.code LIKE CONCAT(:code, '%') ORDER BY c.code DESC LIMIT 1")
    CityCode findTopByCodeLikeOrderByCodeDesc(@Param("code") String code);

}
