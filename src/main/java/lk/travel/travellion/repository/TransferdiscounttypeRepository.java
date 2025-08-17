package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Transferdiscounttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferdiscounttypeRepository extends JpaRepository<Transferdiscounttype, Integer> {
}
