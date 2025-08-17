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
@Table(name = "customerpaymentinformation")
public class Customerpaymentinformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 45)
    @Column(name = "chequenumber", length = 45)
    private String chequenumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerpayment_id", nullable = false)
    private Customerpayment customerpayment;

}
