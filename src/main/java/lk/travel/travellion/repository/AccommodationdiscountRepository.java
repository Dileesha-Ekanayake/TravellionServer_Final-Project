package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Accommodationdiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationdiscountRepository extends JpaRepository<Accommodationdiscount, Integer> {
}
