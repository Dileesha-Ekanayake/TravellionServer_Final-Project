package lk.travel.travellion.entity;

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
@Table(name = "customerpayment")
public class Customerpayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 45)
    @Column(name = "code", length = 45)
    private String code;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "previousamount", precision = 10, scale = 2)
    private BigDecimal previousamount;

    @Column(name = "paidamount", precision = 10, scale = 2)
    private BigDecimal paidamount;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "paymenttype_id", nullable = false)
    private Paymenttype paymenttype;

    @Column(name = "createdon")
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @OneToMany(mappedBy = "customerpayment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Customerpaymentinformation> customerpaymentinformations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customerpayment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Customerpaymentreceipt> customerpaymentreceipts = new LinkedHashSet<>();

}
