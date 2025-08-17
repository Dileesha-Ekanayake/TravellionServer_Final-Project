package lk.travel.travellion.dto.supplierpaymentdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Paymentstatus;
import lk.travel.travellion.entity.Suppliertype;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplierpayment}
 */
@Value
public class SupplierpaymentResponseDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String code;
    LocalDate date;
    BigDecimal previousamount;
    BigDecimal paidamount;
    BigDecimal balance;
    Timestamp createdon;
    Timestamp updatedon;
    @NotNull
    SupplierpaymentResponseDTO.SupplierDto supplier;
    @NotNull
    SupplierpaymentResponseDTO.UserDto user;
    Set<SupplierpaymentitemDto> supplierpaymentitems;
    @NotNull
    Paymentstatus paymentstatus;

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
        @Size(max = 10)
        String mobile;
        @Size(max = 100)
        String email;
        @Size(max = 45)
        String bankAccount;
        @NotNull
        Suppliertype suppliertype;
    }

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
     * DTO for {@link lk.travel.travellion.entity.Supplierpaymentitem}
     */
    @Value
    public static class SupplierpaymentitemDto implements Serializable {
        Integer id;
        @Size(max = 45)
        String item;
        BigDecimal totalpaid;
    }
}
