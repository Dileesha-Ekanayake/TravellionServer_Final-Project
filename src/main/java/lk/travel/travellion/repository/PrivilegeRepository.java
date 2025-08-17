package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import lk.travel.travellion.entity.Module;
import lk.travel.travellion.entity.Privilege;
import lk.travel.travellion.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    void deleteAllByModule_Id(Integer moduleId);

    void deleteAllByRole_IdAndModule_Id(Integer roleId, Integer moduleId);
}
