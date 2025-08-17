package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bookingtour")
public class Bookingtour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tourcontract_id", nullable = false)
    private Tourcontract tourcontract;

    @Column(name = "totalamount", precision = 10, scale = 2)
    private BigDecimal totalamount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bookingitemstatus_id", nullable = false)
    private Bookingitemstatus bookingitemstatus;

    @Column(name = "suppliersamount", precision = 10, scale = 2)
    private BigDecimal suppliersamount;

}
