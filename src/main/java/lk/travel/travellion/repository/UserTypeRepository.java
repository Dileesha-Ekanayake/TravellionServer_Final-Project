package lk.travel.travellion.repository;

import lk.travel.travellion.entity.Usertype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<Usertype, Integer> {
}
