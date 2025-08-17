package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bookingtransfer")
public class Bookingtransfer {
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
    @JoinColumn(name = "transfercontract_id", nullable = false)
    private Transfercontract transfercontract;

    @Column(name = "totalamount", precision = 10, scale = 2)
    private BigDecimal totalamount;

    @Column(name = "discountamount", precision = 10, scale = 2)
    private BigDecimal discountamount;

    @Column(name = "supplieramount", precision = 10, scale = 2)
    private BigDecimal supplieramount;

    @Column(name = "fromdatetime")
    private Timestamp fromdatetime;

    @Column(name = "todatetime")
    private Timestamp todatetime;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bookingitemstatus_id", nullable = false)
    private Bookingitemstatus bookingitemstatus;

    @OneToMany(mappedBy = "bookingtransfer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookingtransferdetail> bookingtransferdetails = new LinkedHashSet<>();

}
