package lk.travel.travellion.dto.customerdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Relationship}
 */
@Value
public class RelationshipDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
