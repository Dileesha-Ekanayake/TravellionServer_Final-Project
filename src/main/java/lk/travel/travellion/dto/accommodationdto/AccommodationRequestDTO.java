package lk.travel.travellion.dto.accommodationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Accommodation}
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@Value
public class AccommodationRequestDTO implements Serializable {
    Integer id;
    @NotNull
    User user;
    @NotNull
    Supplier supplier;
    @NotNull
    @Size(max = 45)
    @Pattern(regexp = "^[A-Z]{3}_[A-Z]{3}_[A-Z0-9]*_[0-9]{2}_[0-9]{3}$", message = "Invalid Reference")
    String reference;
    @Size(max = 100)
    @Pattern(regexp = "^([A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.\\&]+([\\s][A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.\\&]+)*)([\\s])?$", message = "Invalid Name")
    String name;
    String location;
    LocalDate validfrom;
    LocalDate validto;
    LocalDate salesfrom;
    LocalDate salesto;
//    @Pattern(regexp = "^(100|[1-9]?[0-9]|0)?(\\.\\d{1,2})?$", message = "Invalid Markup")
    BigDecimal markup;
    @NotNull
    Accommodationstatus accommodationstatus;
    @NotNull
    Residenttype residenttype;
    @NotNull
    Currency currency;
    Set<Accommodationcncelationcharge> accommodationcncelationcharges;
    Set<Accommodationdiscount> accommodationdiscounts;
    Set<Accommodationroom> accommodationrooms;
    @NotNull
    Accommodationtype accommodationtype;
    @NotNull
    Starrating starrating;
}
