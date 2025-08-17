package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accommodationoccupanciespax", indexes = {
        @Index(name = "fk_accommodationroom_has_paxtype_accommodationroom1_idx", columnList = "accommodationroom_id"),
        @Index(name = "fk_accommodationroom_has_paxtype_paxtype1_idx", columnList = "paxtype_id")
})
public class Accommodationoccupanciespax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodationroom_id", nullable = false)
    private Accommodationroom accommodationroom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "paxtype_id", nullable = false)
    private Paxtype paxtype;

    @Column(name = "count")
    private Integer count;

}
