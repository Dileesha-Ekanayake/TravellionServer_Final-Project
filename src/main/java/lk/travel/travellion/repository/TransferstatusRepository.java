package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Transferstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferstatusRepository extends JpaRepository<Transferstatus, Integer> {
}
