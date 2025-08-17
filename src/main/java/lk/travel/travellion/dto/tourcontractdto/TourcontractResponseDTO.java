package lk.travel.travellion.dto.tourcontractdto;

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
 * DTO for {@link lk.travel.travellion.entity.Tourcontract}
 */
@Value
public class TourcontractResponseDTO implements Serializable {
    Integer id;
    UserDTO user;
    @NotNull
    @Size(max = 45)
    String reference;
    LocalDate validfrom;
    LocalDate validto;
    LocalDate salesfrom;
    LocalDate salesto;
    BigDecimal markup;
    Timestamp createdon;
    Timestamp updatedon;
    Set<TourAccommodationDto> touraccommodations;
    Set<TourTransferContractDto> tourtransfercontracts;
    Set<TourGenericDto> tourgenerics;
    Set<TourOccupancyDto> touroccupancies;
    @Size(max = 100)
    String name;
    @NotNull
    Tourtype tourtype;
    @NotNull
    Tourcategory tourcategory;
    @NotNull
    Tourtheme tourtheme;
    Integer maxpaxcount;


    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDTO implements Serializable {
        Integer id;
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Accommodation}
     */
    @Value
    public static class AccommodationDto implements Serializable {
        Integer id;
        String name;
        String reference;
        String location;
        Starrating starrating;
        Accommodationtype accommodationtype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Touraccommodationroom}
     */
    @Value
    public static class TouraccommodationroomDto implements Serializable {
        Integer id;
        String roomtype;

    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Touraccommodation}
     */
    @Value
    public static class TourAccommodationDto implements Serializable {
        Integer id;
        AccommodationDto accommodation;
        Integer day;
        Set<TouraccommodationroomDto> touraccommodationrooms;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Transfercontract}
     */
    @Value
    public static class TransferContractDto implements Serializable {
        Integer id;
        String reference;
        Transfertype transfertype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Tourtransfercontract}
     */
    @Value
    public static class TourTransferContractDto implements Serializable {
        Integer id;
        TransferContractDto transfercontract;
        Integer day;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Generic}
     */
    @Value
    public static class GenericDto implements Serializable {
        Integer id;
        String name;
        String reference;
        Generictype generictype;
        Currency currency;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Tourtransfercontract}
     */
    @Value
    public static class TourGenericDto implements Serializable {
        Integer id;
        GenericDto generic;
        Integer day;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Touroccupancy}
     */
    @Value
    public static class TourOccupancyDto implements Serializable {
        Integer id;
        Paxtype paxtype;
        Integer amount;
    }
}
