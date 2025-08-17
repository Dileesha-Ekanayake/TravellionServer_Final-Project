package lk.travel.travellion.dto.employeedto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Designation;
import lk.travel.travellion.entity.Employeestatus;
import lk.travel.travellion.entity.Employeetype;
import lk.travel.travellion.entity.Gender;
import lk.travel.travellion.uitl.regexProvider.RegexPattern;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.Employee}
 */
@Value
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EmployeeRequestDTO implements Serializable {
    Integer id;
    @Size(max = 6)
    @Pattern(regexp = "^E\\d{5}$", message = "Invalid Number")
    String number;
    @Size(max = 255)
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    String fullname;
    @Size(max = 45)
    @Pattern(regexp = "^([A-Z][a-z]+)$", message = "Invalid Callingname")
    String callingname;
    byte[] photo;
    @RegexPattern(reg = "^(?:\\d{4}-\\d{2}-\\d{2}|\\d{2}/\\d{2}/\\d{4})$", msg = "Invalid Date Format")
    LocalDate dobirth;
    @Size(max = 12)
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    String nic;
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    String address;
    @Size(max = 10)
    @Pattern(regexp = "^((\\+94|0)(70|71|72|74|75|76|77|78)\\d{7})$", message = "Invalid Mobile Number")
    String mobile;
    @Size(max = 10)
    @Pattern(regexp = "^0[1-9]{1}[0-9]{1}-?[0-9]{6,7}$", message = "Invalid Land-phone Number")
    String land;
    @Size(max = 45)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid Email")
    String email;
    LocalDate doassignment;
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    String description;
    @NotNull
    Gender gender;
    @NotNull
    Designation designation;
    @NotNull
    Employeetype employeetype;
    @NotNull
    Employeestatus employeestatus;
}
