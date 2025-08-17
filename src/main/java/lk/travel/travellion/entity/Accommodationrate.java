package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accommodationrates", indexes = {
        @Index(name = "fk_accommodationrates_accommodationroom1_idx", columnList = "accommodationroom_id"),
        @Index(name = "fk_accommodationrates_paxtype1_idx", columnList = "paxtype_id")
})
public class Accommodationrate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodationroom_id", nullable = false)
    private Accommodationroom accommodationroom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "paxtype_id", nullable = false)
    private Paxtype paxtype;

}
