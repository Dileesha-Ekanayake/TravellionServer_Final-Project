package lk.travel.travellion.dto.accommodationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Accommodation}
 */
@Value
public class AccommodationResponseDTO implements Serializable {
    Integer id;
    UserDto user;
    SupplierDto supplier;
    @NotNull
    @Size(max = 45)
    String reference;
    @Size(max = 100)
    String name;
    String location;
    LocalDate validfrom;
    LocalDate validto;
    LocalDate salesfrom;
    LocalDate salesto;
    BigDecimal markup;
    Timestamp createdon;
    Timestamp updatedon;
    Accommodationstatus accommodationstatus;
    Residenttype residenttype;
    Currency currency;
    Set<AccommodationcncelationchargeDto> accommodationcncelationcharges;
    Set<AccommodationdiscountDto> accommodationdiscounts;
    Set<AccommodationroomDto> accommodationrooms;
    Accommodationtype accommodationtype;
    Starrating starrating;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Supplier}
     */
    @Value
    public static class SupplierDto implements Serializable {
        Integer id;
        String name;
        String brno;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Accommodationcncelationcharge}
     */
    @Value
    public static class AccommodationcncelationchargeDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Ratetype ratetype;
        Cancellationscheme cancellationscheme;
    }


    /**
     * DTO for {@link lk.travel.travellion.entity.Accommodationdiscount}
     */
    @Value
    public static class AccommodationdiscountDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Accommodationdiscounttype accommodationdiscounttype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Accommodationroom}
     */
    @Value
    public static class AccommodationroomDto implements Serializable {
        Integer id;
        Integer rooms;
        Roomtype roomtype;
        Set<Accommodationfacility> accommodationfacilities;
        Set<AccommodationoccupanciespaxDto> accommodationoccupanciespaxes;
        Set<AccommodationrateDto> accommodationrates;

        /**
         * DTO for {@link lk.travel.travellion.entity.Accommodationoccupanciespax}
         */
        @Value
        public static class AccommodationoccupanciespaxDto implements Serializable {
            Integer id;
            Integer count;
            Paxtype paxtype;
        }

        /**
         * DTO for {@link lk.travel.travellion.entity.Accommodationrate}
         */
        @Value
        public static class AccommodationrateDto implements Serializable {
            Integer id;
            BigDecimal amount;
            Paxtype paxtype;
        }
    }
}
