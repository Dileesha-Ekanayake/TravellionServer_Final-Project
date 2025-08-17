package lk.travel.travellion.dto.bookingdto;

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
 * DTO for {@link Booking}
 */
@Value
public class BookingViewResponseDTO implements Serializable {
    Integer id;
    @Size(max = 24)
    String code;
    BigDecimal grossamount;
    BigDecimal discountamount;
    BigDecimal netamount;
    BigDecimal totalpaid;
    BigDecimal balance;
    LocalDate departuredate;
    LocalDate enddate;
    Timestamp createdon;
    Timestamp updatedon;
    @NotNull
    Bookingstatus bookingstatus;
    Set<BookingaccommodationDto> bookingaccommodations;
    Set<BookinggenericDto> bookinggenerics;
    Set<BookingpassengerDto> bookingpassengers;
    Set<BookingtourDto> bookingtours;
    Set<BookingtransferDto> bookingtransfers;
    Set<CustomerpaymentDto> customerpayments;
    @NotNull
    BookingViewResponseDTO.UserDto user;

    /**
     * DTO for {@link Bookingaccommodation}
     */
    @Value
    public static class BookingaccommodationDto implements Serializable {
        Integer id;
        @NotNull
        BookingViewResponseDTO.BookingaccommodationDto.AccommodationDto accommodation;
        BigDecimal totalamount;
        BigDecimal discountamount;
        Timestamp fromdatetime;
        Timestamp todatetime;
        BigDecimal supplieramount;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        Set<BookingaccommodationroomDto> bookingaccommodationrooms;

        /**
         * DTO for {@link Accommodation}
         */
        @Value
        public static class AccommodationDto implements Serializable {
            Integer id;
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
        }

        /**
         * DTO for {@link Bookingaccommodationroom}
         */
        @Value
        public static class BookingaccommodationroomDto implements Serializable {
            Integer id;
            @Size(max = 45)
            String roomtype;
            @Size(max = 45)
            String paxtype;
            Integer count;
            BigDecimal amount;
        }
    }

    /**
     * DTO for {@link Bookinggeneric}
     */
    @Value
    public static class BookinggenericDto implements Serializable {
        Integer id;
        @NotNull
        BookingViewResponseDTO.BookinggenericDto.GenericDto generic;
        BigDecimal discountamount;
        BigDecimal totalamount;
        BigDecimal supplieramount;
        Timestamp fromdatetime;
        Timestamp todatetime;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        Set<BookinggenericpaxDto> bookinggenericpaxes;

        /**
         * DTO for {@link Generic}
         */
        @Value
        public static class GenericDto implements Serializable {
            Integer id;
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
        }

        /**
         * DTO for {@link Bookinggenericpax}
         */
        @Value
        public static class BookinggenericpaxDto implements Serializable {
            Integer id;
            @Size(max = 45)
            String paxtype;
            Integer paxcount;
            BigDecimal amount;
        }
    }

    /**
     * DTO for {@link Bookingpassenger}
     */
    @Value
    public static class BookingpassengerDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String code;
        @Size(max = 100)
        String name;
        Integer age;
        Boolean leadpassenger;
    }

    /**
     * DTO for {@link Bookingtour}
     */
    @Value
    public static class BookingtourDto implements Serializable {
        Integer id;
        @NotNull
        BookingViewResponseDTO.BookingtourDto.TourcontractDto tourcontract;
        BigDecimal totalamount;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        BigDecimal suppliersamount;

        /**
         * DTO for {@link Tourcontract}
         */
        @Value
        public static class TourcontractDto implements Serializable {
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
            @NotNull
            Tourtype tourtype;
            @NotNull
            Tourcategory tourcategory;
            @NotNull
            Tourtheme tourtheme;
        }
    }

    /**
     * DTO for {@link Bookingtransfer}
     */
    @Value
    public static class BookingtransferDto implements Serializable {
        Integer id;
        @NotNull
        BookingViewResponseDTO.BookingtransferDto.TransfercontractDto transfercontract;
        BigDecimal totalamount;
        BigDecimal discountamount;
        BigDecimal supplieramount;
        Timestamp fromdatetime;
        Timestamp todatetime;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        Set<BookingtransferdetailDto> bookingtransferdetails;

        /**
         * DTO for {@link Transfercontract}
         */
        @Value
        public static class TransfercontractDto implements Serializable {
            Integer id;
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
        }

        /**
         * DTO for {@link Bookingtransferdetail}
         */
        @Value
        public static class BookingtransferdetailDto implements Serializable {
            Integer id;
            @Size(max = 45)
            String pickuplocation;
            @Size(max = 45)
            String droplocation;
            @Size(max = 45)
            String paxtype;
            Integer paxcount;
            BigDecimal totalamount;
        }
    }

    /**
     * DTO for {@link Customerpayment}
     */
    @Value
    public static class CustomerpaymentDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String code;
        LocalDate date;
        BigDecimal previousamount;
        BigDecimal paidamount;
        BigDecimal balance;
        @NotNull
        Paymenttype paymenttype;
        Timestamp createdon;
        Timestamp updatedon;
    }

    /**
     * DTO for {@link User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String username;
    }
}
