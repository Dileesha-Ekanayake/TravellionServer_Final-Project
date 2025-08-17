package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Genericdiscounttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericdiscounttypeRepository extends JpaRepository<Genericdiscounttype, Integer> {
}
