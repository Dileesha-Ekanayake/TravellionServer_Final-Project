package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Accommodationdiscounttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationdiscounttypeRepository extends JpaRepository<Accommodationdiscounttype, Integer> {
}
