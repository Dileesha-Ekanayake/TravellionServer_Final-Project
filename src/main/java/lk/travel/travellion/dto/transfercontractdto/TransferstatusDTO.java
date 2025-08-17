package lk.travel.travellion.dto.transfercontractdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Transferstatus}
 */
@Value
public class TransferstatusDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
