package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bookinggenericpax")
public class Bookinggenericpax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookinggeneric_id", nullable = false)
    private Bookinggeneric bookinggeneric;

    @Size(max = 45)
    @Column(name = "paxtype", length = 45)
    private String paxtype;

    @Column(name = "paxcount")
    private Integer paxcount;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

}
