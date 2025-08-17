package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Ratetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatetypeRepository extends JpaRepository<Ratetype, Integer> {
    boolean existsByName(@Size(max = 45) String name);

    boolean existsByNameAndIdNot(@Size(max = 45) String name, Integer id);
}
