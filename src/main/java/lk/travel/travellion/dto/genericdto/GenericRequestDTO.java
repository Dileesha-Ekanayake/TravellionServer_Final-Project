package lk.travel.travellion.dto.genericdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Generic}
 */
@Value
@NoArgsConstructor(force = true)
public class GenericRequestDTO implements Serializable {

    Integer id;
    UserDto user;
    @Size(max = 255)
    @Pattern(regexp = "^([A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.]+([\\s][A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.]+)*)([\\s])?$", message = "Invalid Name")
    String name;
    @NotNull
    @Size(max = 45)
    @Pattern(regexp = "^[A-Z]{3}_[0-9]{2}_[0-9]{4}$", message = "Invalid Reference")
    String reference;
    LocalDate validfrom;
    LocalDate validto;
    LocalDate salesfrom;
    LocalDate salesto;
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
    Supplier supplier;
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
}
