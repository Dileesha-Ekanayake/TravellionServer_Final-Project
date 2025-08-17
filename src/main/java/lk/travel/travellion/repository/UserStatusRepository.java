package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Userstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<Userstatus, Integer> {
}
