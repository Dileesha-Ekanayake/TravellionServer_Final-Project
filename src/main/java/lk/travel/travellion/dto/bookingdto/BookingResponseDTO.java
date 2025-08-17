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
 * DTO for {@link lk.travel.travellion.entity.Booking}
 */
@Value
public class BookingResponseDTO implements Serializable {
    Integer id;
    @NotNull
    UserDto user;
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
//        AccommodationDto accommodation;
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

        /**
         * DTO for {@link lk.travel.travellion.entity.Accommodation}
         */
        @Value
        public static class AccommodationDto implements Serializable {
            Integer id;
            String reference;
            String name;
            String location;
        }
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Bookinggeneric}
     */
    @Value
    public static class BookinggenericDto implements Serializable {
        Integer id;
        @NotNull
//        GenericDto generic;
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

        /**
         * DTO for {@link lk.travel.travellion.entity.Generic}
         */
        @Value
        public static class GenericDto implements Serializable {
            Integer id;
            String name;
            @NotNull
            String reference;
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
        String name;
        Integer age;
        Boolean leadpassenger;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Bookingtour}
     */
    @Value
    public static class BookingtourDto implements Serializable {
        Integer id;
        @NotNull
//        TourcontractDto tourcontract;
        BigDecimal totalamount;
        @NotNull
        Bookingitemstatus bookingitemstatus;
        BigDecimal suppliersamount;

        /**
         * DTO for {@link lk.travel.travellion.entity.Tourcontract}
         */
        @Value
        public static class TourcontractDto implements Serializable {
            Integer id;
            String name;
            String reference;
        }
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Bookingtransfer}
     */
    @Value
    public static class BookingtransferDto implements Serializable {
        Integer id;
        @NotNull
//        TransfercontractDto transfercontract;
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

        /**
         * DTO for {@link lk.travel.travellion.entity.Transfercontract}
         */
        @Value
        public static class TransfercontractDto implements Serializable {
            Integer id;
            String reference;
        }
    }
}
