package lk.travel.travellion.dto.tourcontractdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.Tourcontract}
 */
@Value
public class TourcontractSearchDTO implements Serializable {
    @Size(max = 100)
    String name;
    LocalDate salesfrom;
    LocalDate salesto;
    String tourtype;
    String tourcategory;
    String tourtheme;
    Integer paxcount;
}
