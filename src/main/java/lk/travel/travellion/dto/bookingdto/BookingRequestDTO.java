package lk.travel.travellion.dto.bookingdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Booking}
 */
@Value
@NoArgsConstructor(force = true)
public class BookingRequestDTO implements Serializable {
    Integer id;
    @NotNull
    UserDto user;
    @Size(max = 24)
    @Pattern(regexp = "^[A-Z]{4}_[0-9]{2}_[0-9]{4}$", message = "Invalid Code")
    String code;

//    @Pattern(regexp = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$", message = "Invalid gross amount - must be positive number with 0 or 2 decimal places")
    BigDecimal grossamount;

//    @Pattern(regexp = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$", message = "Invalid discount amount - must be positive number with 0 or 2 decimal places")
    BigDecimal discountamount;

//    @Pattern(regexp = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$", message = "Invalid net amount - must be positive number with 0 or 2 decimal places")
    BigDecimal netamount;

//    @Pattern(regexp = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$", message = "Invalid total paid - must be positive number with 0 or 2 decimal places")
    BigDecimal totalpaid;

//    @Pattern(regexp = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$", message = "Invalid balance - must be positive number with 0 or 2 decimal places")
    BigDecimal balance;
    LocalDate departuredate;
    LocalDate enddate;
    @NotNull
    Bookingstatus bookingstatus;
    Set<BookingaccommodationDto> bookingaccommodations;
    Set<BookinggenericDto> bookinggenerics;
    Set<BookingpassengerDto> bookingpassengers;
    Set<BookingtourDto> bookingtours;
    Set<BookingtransferDto> bookingtransfers;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Bookingaccommodation}
     */
    @Value
    public static class BookingaccommodationDto implements Serializable {
        Integer id;
        @NotNull
        Accommodation accommodation;
        BigDecimal totalamount;
        Timestamp fromdatetime;
        Timestamp todatetime;
        BigDecimal supplieramount;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        Set<BookingaccommodationroomDto> bookingaccommodationrooms;
        BigDecimal discountamount;

        /**
         * DTO for {@link lk.travel.travellion.entity.Bookingaccommodationroom}
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
     * DTO for {@link lk.travel.travellion.entity.Bookinggeneric}
     */
    @Value
    public static class BookinggenericDto implements Serializable {
        Integer id;
        @NotNull
        Generic generic;
        BigDecimal totalamount;
        Timestamp fromdatetime;
        Timestamp todatetime;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        Set<BookinggenericpaxDto> bookinggenericpaxes;
        BigDecimal discountamount;
        BigDecimal supplieramount;

        /**
         * DTO for {@link lk.travel.travellion.entity.Bookinggenericpax}
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
     * DTO for {@link lk.travel.travellion.entity.Bookingpassenger}
     */
    @Value
    public static class BookingpassengerDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String code;
        Integer age;
        String name;
        Boolean leadpassenger;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Bookingtour}
     */
    @Value
    public static class BookingtourDto implements Serializable {
        Integer id;
        @NotNull
        Tourcontract tourcontract;
        BigDecimal totalamount;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        BigDecimal suppliersamount;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Bookingtransfer}
     */
    @Value
    public static class BookingtransferDto implements Serializable {
        Integer id;
        @NotNull
        Transfercontract transfercontract;
        BigDecimal totalamount;
        Timestamp fromdatetime;
        Timestamp todatetime;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        Set<BookingtransferdetailDto> bookingtransferdetails;
        BigDecimal discountamount;
        BigDecimal supplieramount;

        /**
         * DTO for {@link lk.travel.travellion.entity.Bookingtransferdetail}
         */
        @Value
        public static class BookingtransferdetailDto implements Serializable {
            Integer id;
            @Size(max = 45)
            String pickuplocation;
            @Size(max = 45)
            String droplocation;
            Integer paxcount;
            BigDecimal totalamount;
            @Size(max = 45)
            String paxtype;
        }
    }
}
