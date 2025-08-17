package lk.travel.travellion.service.auth;

import lk.travel.travellion.dto.privilegedto.PrivilegeDTO;
import lk.travel.travellion.entity.Privilege;

import java.util.List;

public interface PrivilegeService {

    List<PrivilegeDTO> getAllPrivileges();

    void savePrivilege(List<PrivilegeDTO> privileges);

    Privilege updatePrivilege(PrivilegeDTO privilege);

    void deletePrivilege(Integer id);
}
