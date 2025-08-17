package lk.travel.travellion.dto.genericdto;

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
 * DTO for {@link lk.travel.travellion.entity.Generic}
 */
@Value
public class GenericResponseDTO implements Serializable {
    Integer id;
    UserDto user;
    @Size(max = 255)
    String name;
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
    Genericstatus genericstatus;
    Set<GenericrateDto> genericrates;
    Set<GenericcancellationchargeDto> genericcancellationcharges;
    Set<GenericdiscountDto> genericdiscounts;
    @NotNull
    Generictype generictype;
    @NotNull
    Currency currency;
    @NotNull
    SupplierDto supplier;
    BigDecimal markup;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Genericrate}
     */
    @Value
    public static class GenericrateDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Paxtype paxtype;
        Residenttype residenttype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Genericcancellationcharge}
     */
    @Value
    public static class GenericcancellationchargeDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Ratetype ratetype;
        Cancellationscheme cancellationscheme;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Genericdiscount}
     */
    @Value
    public static class GenericdiscountDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Genericdiscounttype genericdiscounttype;
        Ratetype ratetype;
    }

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
        @Size(max = 10)
        String mobile;
        @Size(max = 100)
        String email;
        @Size(max = 45)
        String bankAccount;
    }
}
