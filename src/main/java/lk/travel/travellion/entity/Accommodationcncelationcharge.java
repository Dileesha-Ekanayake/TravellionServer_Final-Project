package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accommodationcncelationcharges", indexes = {
        @Index(name = "fk_accommodationcncelationcharges_accommodation1_idx", columnList = "accommodation_id"),
        @Index(name = "fk_accommodationcncelationcharges_ratetype1_idx", columnList = "ratetype_id"),
        @Index(name = "fk_accommodationcncelationcharges_cancellationscheme1_idx", columnList = "cancellationscheme_id")
})
public class Accommodationcncelationcharge {
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
    @JoinColumn(name = "ratetype_id", nullable = false)
    private Ratetype ratetype;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cancellationscheme_id", nullable = false)
    private Cancellationscheme cancellationscheme;

}
