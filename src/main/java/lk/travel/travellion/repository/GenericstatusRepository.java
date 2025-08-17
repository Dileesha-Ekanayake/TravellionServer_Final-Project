package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Genericstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericstatusRepository extends JpaRepository<Genericstatus, Integer> {
}
