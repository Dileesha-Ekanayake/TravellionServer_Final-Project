package lk.travel.travellion.dto.userdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Usertype}
 */
@Value
public class UsertypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
