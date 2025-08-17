package lk.travel.travellion.dto.userdto;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.User;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.User}
 */
@Value
public class UserListDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String username;
    EmployeeDTO employee;

    /**
     * DTO for {@link lk.travel.travellion.entity.Employee}
     */
    @Value
    public static class EmployeeDTO implements Serializable {
        String callingname;

    }
}
