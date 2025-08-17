package lk.travel.travellion.dto.transfercontractdto;

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
 * DTO for {@link lk.travel.travellion.entity.Transfercontract}
 */
@Value
public class TransfercontractResponseDTO implements Serializable {
    Integer id;
    UserDto user;
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
    Transferstatus transferstatus;
    Currency currency;
    TransferDto transfer;
    Set<TransfercancellationchargeDto> transfercancellationcharges;
    Set<TransferdiscountDto> transferdiscounts;
    Set<TransferrateDto> transferrates;
    @NotNull
    Transfertype transfertype;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Supplier}
     */
    @Value
    public static class SupplierDto implements Serializable {
        Integer id;
        String name;
        String brno;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Transfer}
     */
    @Value
    public static class TransferDto implements Serializable {
        Integer id;
        Boolean isreturn;
        Set<PickuplocationDto> pickuplocations;
        Set<DroplocationDto> droplocations;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Droplocation}
     */
    @Value
    public static class DroplocationDto implements Serializable {
        Integer id;
        String code;
        String name;
        Locationtype locationtype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Pickuplocation}
     */
    @Value
    public static class PickuplocationDto implements Serializable {
        Integer id;
        String code;
        String name;
        Locationtype locationtype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Transfercancellationcharge}
     */
    @Value
    public static class TransfercancellationchargeDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Ratetype ratetype;
        Cancellationscheme cancellationscheme;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Transferdiscount}
     */
    @Value
    public static class TransferdiscountDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Transferdiscounttype transferdiscounttype;
        Ratetype ratetype;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Transferrate}
     */
    @Value
    public static class TransferrateDto implements Serializable {
        Integer id;
        BigDecimal amount;
        Paxtype paxtype;
    }
}
