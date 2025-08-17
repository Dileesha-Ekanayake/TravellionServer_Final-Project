package lk.travel.travellion.dto.employeedto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Designation;
import lk.travel.travellion.entity.Employeestatus;
import lk.travel.travellion.entity.Employeetype;
import lk.travel.travellion.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.Employee}
 */
@Value
@Getter
@Setter
@AllArgsConstructor
public class EmployeeResponseDTO implements Serializable {
    Integer id;
    @Size(max = 6)
    String number;
    @Size(max = 255)
    String fullname;
    @Size(max = 45)
    String callingname;
    byte[] photo;
    LocalDate dobirth;
    @Size(max = 12)
    String nic;
    String address;
    @Size(max = 10)
    String mobile;
    @Size(max = 10)
    String land;
    @Size(max = 45)
    String email;
    LocalDate doassignment;
    String description;
    Timestamp createdon;
    @NotNull
    Gender gender;
    @NotNull
    Designation designation;
    @NotNull
    Employeetype employeetype;
    @NotNull
    Employeestatus employeestatus;
}
