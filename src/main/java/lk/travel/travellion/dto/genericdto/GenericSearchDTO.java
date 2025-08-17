package lk.travel.travellion.dto.genericdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.Generic}
 */
@Value
public class GenericSearchDTO implements Serializable {
    @Size(max = 255)
    String name;
    LocalDate salesfrom;
    LocalDate salesto;
    String generictype;

}
