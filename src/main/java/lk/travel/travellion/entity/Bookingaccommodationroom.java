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
@Table(name = "bookingaccommodationroom")
public class Bookingaccommodationroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "roomtype", length = 45)
    private String roomtype;

    @Size(max = 45)
    @Column(name = "paxtype", length = 45)
    private String paxtype;

    @Column(name = "count")
    private Integer count;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookingaccommodation_id", nullable = false)
    private Bookingaccommodation bookingaccommodation;

}
