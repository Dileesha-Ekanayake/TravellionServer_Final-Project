package lk.travel.travellion.dto.supplierpaymentdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Paymentstatus;
import lk.travel.travellion.entity.Supplier;
import lk.travel.travellion.entity.User;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplierpayment}
 */
@Value
public class SupplierpaymentRequestDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String code;
    LocalDate date;
    BigDecimal previousamount;
    BigDecimal paidamount;
    BigDecimal balance;
    @NotNull
    Supplier supplier;
    @NotNull
    User user;
    Set<SupplierpaymentitemDto> supplierpaymentitems;
    @NotNull
    Paymentstatus paymentstatus;

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
