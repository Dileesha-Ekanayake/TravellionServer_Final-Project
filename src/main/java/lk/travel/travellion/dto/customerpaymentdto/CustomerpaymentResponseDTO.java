package lk.travel.travellion.dto.customerpaymentdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Paymenttype;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Customerpayment}
 */
@Value
public class CustomerpaymentResponseDTO implements Serializable {
    Integer id;
    @NotNull
    CustomerpaymentResponseDTO.UserDto user;
    @Size(max = 45)
    String code;
    LocalDate date;
    BigDecimal previousamount;
    BigDecimal paidamount;
    BigDecimal balance;
    @NotNull
    CustomerpaymentResponseDTO.BookingDto booking;
    @NotNull
    CustomerpaymentResponseDTO.CustomerDto customer;
    @NotNull
    Paymenttype paymenttype;
    Timestamp createdon;
    Timestamp updatedon;
    Set<CustomerpaymentinformationDto> customerpaymentinformations;
    Set<CustomerpaymentreceiptDto> customerpaymentreceipts;

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
     * DTO for {@link lk.travel.travellion.entity.Booking}
     */
    @Value
    public static class BookingDto implements Serializable {
        Integer id;
        @Size(max = 24)
        String code;
        BigDecimal grossamount;
        BigDecimal discountamount;
        BigDecimal netamount;
        BigDecimal totalpaid;
        BigDecimal balance;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Customer}
     */
    @Value
    public static class CustomerDto implements Serializable {
        Integer id;
        @Size(max = 11)
        String code;
        @Size(max = 100)
        String fullname;
        @Size(max = 45)
        String callingname;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Customerpaymentinformation}
     */
    @Value
    public static class CustomerpaymentinformationDto implements Serializable {
        Integer id;
        BigDecimal amount;
        @Size(max = 45)
        String chequenumber;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Customerpaymentreceipt}
     */
    @Value
    public static class CustomerpaymentreceiptDto implements Serializable {
        Integer id;
        byte[] receipt;
    }
}
