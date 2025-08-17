package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortRepository extends JpaRepository<Port, Integer> {
}
