package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Genericrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericrateRepository extends JpaRepository<Genericrate, Integer> {
}
