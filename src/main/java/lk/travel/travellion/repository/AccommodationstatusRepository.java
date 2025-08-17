package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Accommodationstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationstatusRepository extends JpaRepository<Accommodationstatus, Integer> {
}
