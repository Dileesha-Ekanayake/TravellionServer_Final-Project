package lk.travel.travellion.dto.employeedto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Employee}
 */
@Value
public class EmployeeforUserDetailsDTO implements Serializable {
    @Size(max = 6)
    String number;
    @Size(max = 255)
    String fullname;
    @Size(max = 45)
    String callingname;
    byte[] photo;
    @Size(max = 10)
    String mobile;
    @Size(max = 45)
    String email;
    @NotNull
    Gender gender;
    @NotNull
    Designation designation;
    @NotNull
    Employeetype employeetype;
    @NotNull
    Employeestatus employeestatus;
    LocalDate dobirth;
    @Size(max = 12)
    String nic;
    String address;
    @Size(max = 10)
    String land;
    String description;
    Timestamp createdon;
    Set<UserDto> users;

    /**
     * DTO for {@link User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String username;
        @NotNull
        Userstatus userstatus;
        @NotNull
        Usertype usertype;
    }
}
