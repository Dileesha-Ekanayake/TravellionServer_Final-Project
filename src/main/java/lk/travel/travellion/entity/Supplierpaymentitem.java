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
@Table(name = "supplierpaymentitem")
public class Supplierpaymentitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "item", length = 45)
    private String item;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplierpayment_id", nullable = false)
    private Supplierpayment supplierpayment;

    @Column(name = "totalpaid", precision = 10, scale = 2)
    private BigDecimal totalpaid;

}
