package lk.travel.travellion.dto.userdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Employee;
import lk.travel.travellion.entity.Userrole;
import lk.travel.travellion.entity.Userstatus;
import lk.travel.travellion.entity.Usertype;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.User}
 */
@Value
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserRequestDTO implements Serializable {
    Integer id;
    @NotNull
    Employee employee;
    @Size(max = 45)
    @Pattern(regexp = "^([a-zA-Z0-9_.-]+)$", message = "Invalid Username")
    String username;
    @Size(max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Invalid Password")
    String password;
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    String descrption;
    @NotNull
    Boolean accountLocked;
    @NotNull
    Userstatus userstatus;
    @NotNull
    Usertype usertype;
    Set<Userrole> userroles;
}
