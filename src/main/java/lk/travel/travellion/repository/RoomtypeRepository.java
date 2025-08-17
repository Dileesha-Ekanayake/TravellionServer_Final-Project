package lk.travel.travellion.repository;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Roomtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomtypeRepository extends JpaRepository<Roomtype, Integer> {
    boolean existsByName(@Size(max = 45) String name);

    boolean existsByNameAndIdNot(@Size(max = 45) String name, Integer id);
}
