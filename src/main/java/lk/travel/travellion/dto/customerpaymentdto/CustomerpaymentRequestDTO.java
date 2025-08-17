package lk.travel.travellion.dto.customerpaymentdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Customerpayment}
 */
@Value
public class CustomerpaymentRequestDTO implements Serializable {
    Integer id;
    @NotNull
    CustomerpaymentRequestDTO.UserDto user;
    @Size(max = 45)
    String code;
    LocalDate date;
    BigDecimal previousamount;
    BigDecimal paidamount;
    BigDecimal balance;
    @NotNull
    Booking booking;
    @NotNull
    Customer customer;
    @NotNull
    Paymenttype paymenttype;
    Set<Customerpaymentinformation> customerpaymentinformations;
    Set<Customerpaymentreceipt> customerpaymentreceipts;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String username;
    }
}
