package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 24)
    @Column(name = "code", length = 24)
    private String code;

    @Column(name = "grossamount", precision = 10, scale = 2)
    private BigDecimal grossamount;

    @Column(name = "discountamount", precision = 10, scale = 2)
    private BigDecimal discountamount;

    @Column(name = "netamount", precision = 10, scale = 2)
    private BigDecimal netamount;

    @Column(name = "totalpaid", precision = 10, scale = 2)
    private BigDecimal totalpaid;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "departuredate")
    private LocalDate departuredate;

    @Column(name = "enddate")
    private LocalDate enddate;

    @Column(name = "createdon")
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bookingstatus_id", nullable = false)
    private Bookingstatus bookingstatus;

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookingaccommodation> bookingaccommodations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookinggeneric> bookinggenerics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookingpassenger> bookingpassengers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookingtour> bookingtours = new LinkedHashSet<>();

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookingtransfer> bookingtransfers = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private Set<Customerpayment> customerpayments = new LinkedHashSet<>();

}
