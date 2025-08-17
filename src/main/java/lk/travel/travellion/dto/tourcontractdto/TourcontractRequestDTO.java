package lk.travel.travellion.dto.tourcontractdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Tourcontract}
 */
@Value
@NoArgsConstructor(force = true)
public class TourcontractRequestDTO implements Serializable {
    Integer id;
    UserDto user;
    @Pattern(regexp = "^([A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.\\&]+([\\s][A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.\\&]+)*)([\\s])?$", message = "Invalid Name")
    String name;
    @NotNull
    @Size(max = 45)
    @Pattern(regexp = "^[A-Z]{4}_[0-9]{2}_[0-9]{4}$", message = "Invalid Reference")
    String reference;
    LocalDate validfrom;
    LocalDate validto;
    LocalDate salesfrom;
    LocalDate salesto;
    BigDecimal markup;
    Set<TouraccommodationDto> touraccommodations;
    Set<TourgenericDto> tourgenerics;
    Set<TourtransfercontractDto> tourtransfercontracts;
    @NotNull
    Tourtype tourtype;
    @NotNull
    Tourcategory tourcategory;
    @NotNull
    Tourtheme tourtheme;
    Integer maxpaxcount;
    Set<TouroccupancyDto> touroccupancies;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Touraccommodationroom}
     */
    @Value
    public static class TouraccommodationroomDto implements Serializable {
        Integer id;
        String name;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Touraccommodation}
     */
    @Value
    public static class TouraccommodationDto implements Serializable {
        Integer id;
        Integer day;
        Accommodation accommodation;
//        TouraccommodationroomDto touraccommodationroom;
        Set<Touraccommodationroom> touraccommodationrooms;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Tourgeneric}
     */
    @Value
    public static class TourgenericDto implements Serializable {
        Integer id;
        Integer day;
        Generic generic;
    }


    /**
     * DTO for {@link lk.travel.travellion.entity.Tourtransfercontract}
     */
    @Value
    public static class TourtransfercontractDto implements Serializable {
        Integer id;
        Integer day;
        Transfercontract transfercontract;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Touroccupancy}
     */
    @Value
    public static class TouroccupancyDto implements Serializable {
        Integer id;
        Paxtype paxtype;
        Integer amount;
    }
}
