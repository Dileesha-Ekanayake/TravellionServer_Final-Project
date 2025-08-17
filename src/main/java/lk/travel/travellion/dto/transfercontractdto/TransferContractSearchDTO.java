package lk.travel.travellion.dto.transfercontractdto;


import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.Set;

///**
// * DTO for {@link lk.travel.travellion.entity.Transfer}
// */
//@Value
@Getter
@Setter
public class TransferContractSearchDTO {

    Boolean isreturn;
    String droplocation;
    String pickuplocation;
    String transfertype;
}
