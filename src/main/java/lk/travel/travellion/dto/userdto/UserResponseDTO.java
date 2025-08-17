package lk.travel.travellion.dto.userdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Userrole;
import lk.travel.travellion.entity.Userstatus;
import lk.travel.travellion.entity.Usertype;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.User}
 */
@Value
public class UserResponseDTO implements Serializable {
    Integer id;
    EmployeeDTO employee;
    @Size(max = 45)
    String username;
    Timestamp createdon;
    Timestamp updatedon;
    String descrption;
    Boolean accountLocked;
    @NotNull
    Userstatus userstatus;
    @NotNull
    Usertype usertype;
    Set<Userrole> userroles;

    /**
     * DTO for {@link lk.travel.travellion.entity.Employee}
     */
    @Value
    public static class EmployeeDTO implements Serializable {
        Integer id;
        String number;
        String fullname;
        String callingname;
        byte[] photo;

    }
}
