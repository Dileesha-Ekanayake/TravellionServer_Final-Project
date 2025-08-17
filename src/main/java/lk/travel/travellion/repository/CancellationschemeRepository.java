package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Cancellationscheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancellationschemeRepository extends JpaRepository<Cancellationscheme, Integer> {
    boolean existsByName(@Size(max = 45) String name);

    boolean existsByNameAndIdNot(@Size(max = 45) String name, Integer id);
}
