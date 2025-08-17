package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Paxtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaxtypeRepository extends JpaRepository<Paxtype, Integer> {
    boolean existsByName(@Size(max = 45) String name);

    boolean existsByNameAndIdNot(@Size(max = 45) String name, Integer id);
}
