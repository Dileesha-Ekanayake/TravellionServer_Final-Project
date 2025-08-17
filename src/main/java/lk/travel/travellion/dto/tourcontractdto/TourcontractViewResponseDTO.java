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
public class TourcontractViewResponseDTO implements Serializable {
    Integer id;
    @Size(max = 100)
    String name;
    @NotNull
    @Size(max = 45)
    String reference;
    LocalDate validfrom;
    LocalDate validto;
    LocalDate salesfrom;
    LocalDate salesto;
    BigDecimal markup;
    Integer maxpaxcount;
    Timestamp createdon;
    Timestamp updatedon;
    Set<TouraccommodationDto> touraccommodations;
    Set<TourgenericDto> tourgenerics;
    Set<TourtransfercontractDto> tourtransfercontracts;
    Set<TouroccupancyDto> touroccupancies;
    @NotNull
    Tourtype tourtype;
    @NotNull
    Tourcategory tourcategory;
    @NotNull
    Tourtheme tourtheme;

    /**
     * DTO for {@link lk.travel.travellion.entity.Touraccommodation}
     */
    @Value
    public static class TouraccommodationDto implements Serializable {
        Integer id;
        @NotNull
        Integer day;
        @NotNull
        TourcontractViewResponseDTO.TouraccommodationDto.AccommodationDto accommodation;
        Set<TouraccommodationroomDto> touraccommodationrooms;

        /**
         * DTO for {@link lk.travel.travellion.entity.Accommodation}
         */
        @Value
        public static class AccommodationDto implements Serializable {
            Integer id;
            @NotNull
            TourcontractViewResponseDTO.TouraccommodationDto.AccommodationDto.SupplierDto supplier;
            @NotNull
            @Size(max = 45)
            String reference;
            @Size(max = 100)
            String name;
            @Size(max = 100)
            String location;
            LocalDate validfrom;
            LocalDate validto;
            LocalDate salesfrom;
            LocalDate salesto;
            BigDecimal markup;
            Timestamp createdon;
            Timestamp updatedon;
            @NotNull
            Accommodationstatus accommodationstatus;
            @NotNull
            Residenttype residenttype;
            @NotNull
            Currency currency;
            @NotNull
            Accommodationtype accommodationtype;
            @NotNull
            Starrating starrating;

            /**
             * DTO for {@link lk.travel.travellion.entity.Supplier}
             */
            @Value
            public static class SupplierDto implements Serializable {
                Integer id;
                @NotNull
                @Size(max = 10)
                String brno;
                @Size(max = 255)
                String name;
            }
        }

        /**
         * DTO for {@link Touraccommodationroom}
         */
        @Value
        public static class TouraccommodationroomDto implements Serializable {
            Integer id;
            @Size(max = 45)
            String roomtype;
        }
    }

    /**
     * DTO for {@link Tourgeneric}
     */
    @Value
    public static class TourgenericDto implements Serializable {
        Integer id;
        @NotNull
        Integer day;
        @NotNull
        TourcontractViewResponseDTO.TourgenericDto.GenericDto generic;

        /**
         * DTO for {@link Generic}
         */
        @Value
        public static class GenericDto implements Serializable {
            Integer id;
            @NotNull
            TourcontractViewResponseDTO.TourgenericDto.GenericDto.SupplierDto supplier;
            @Size(max = 255)
            String name;
            BigDecimal markup;
            @NotNull
            @Size(max = 45)
            String reference;
            LocalDate validfrom;
            LocalDate validto;
            LocalDate salesfrom;
            LocalDate salesto;
            Timestamp createdon;
            Timestamp updatedon;
            String description;
            @NotNull
            Generictype generictype;
            @NotNull
            Genericstatus genericstatus;
            @NotNull
            Currency currency;

            /**
             * DTO for {@link Supplier}
             */
            @Value
            public static class SupplierDto implements Serializable {
                Integer id;
                @NotNull
                @Size(max = 10)
                String brno;
                @Size(max = 255)
                String name;
            }
        }
    }

    /**
     * DTO for {@link Tourtransfercontract}
     */
    @Value
    public static class TourtransfercontractDto implements Serializable {
        Integer id;
        @NotNull
        Integer day;
        @NotNull
        TourcontractViewResponseDTO.TourtransfercontractDto.TransfercontractDto transfercontract;

        /**
         * DTO for {@link Transfercontract}
         */
        @Value
        public static class TransfercontractDto implements Serializable {
            Integer id;
            SupplierDto supplier;
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
            @NotNull
            Transferstatus transferstatus;
            @NotNull
            Currency currency;
            @NotNull
            Transfertype transfertype;

            /**
             * DTO for {@link Supplier}
             */
            @Value
            public static class SupplierDto implements Serializable {
                Integer id;
                @NotNull
                @Size(max = 10)
                String brno;
                @Size(max = 255)
                String name;
            }
        }
    }

    /**
     * DTO for {@link Touroccupancy}
     */
    @Value
    public static class TouroccupancyDto implements Serializable {
        Integer id;
        @NotNull
        Paxtype paxtype;
        BigDecimal amount;
    }
}
