package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Employee;
import lk.travel.travellion.entity.User;
import lk.travel.travellion.projection.UserAccountLocked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(@Size(max = 45) String username);

    boolean existsByUsernameAndIdNot(@Size(max = 45) String username, Integer id);

    UserAccountLocked findUserAccountLockedByUsername(String username);

    List<User> findUserNameByEmployee(@NotNull Employee employee);

    List<User> findByAccountLockedFalseAndUserstatus_Id(Integer userstatusId);
}
