package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Roomfacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomfacilityRepository extends JpaRepository<Roomfacility, Integer> {
  boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
