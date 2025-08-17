package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accommodationdiscount", indexes = {
        @Index(name = "fk_accommodationdiscount_accommodation1_idx", columnList = "accommodation_id"),
        @Index(name = "fk_accommodationdiscount_accommodationdiscounttype1_idx", columnList = "accommodationdiscounttype_id")
})
public class Accommodationdiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodationdiscounttype_id", nullable = false)
    private Accommodationdiscounttype accommodationdiscounttype;

}
