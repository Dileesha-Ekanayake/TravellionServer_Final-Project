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
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transfercontract")
public class Transfercontract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Size(max = 45)
    @NotNull
    @Column(name = "reference", nullable = false, length = 45)
    private String reference;

    @Column(name = "validfrom")
    private LocalDate validfrom;

    @Column(name = "validto")
    private LocalDate validto;

    @Column(name = "salesfrom")
    private LocalDate salesfrom;

    @Column(name = "salesto")
    private LocalDate salesto;

    @Column(name = "markup", precision = 10, scale = 2)
    private BigDecimal markup;

    @Column(name = "createdon", updatable = false)
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transferstatus_id", nullable = false)
    private Transferstatus transferstatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transfer_id", nullable = false)
    private Transfer transfer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @OneToMany(mappedBy = "transfercontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transfercancellationcharge> transfercancellationcharges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "transfercontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transferdiscount> transferdiscounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "transfercontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transferrate> transferrates = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "transfercontract")
    private Set<Tourtransfercontract> tourtransfercontracts = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transfertype_id", nullable = false)
    private Transfertype transfertype;

    @JsonIgnore
    @OneToMany(mappedBy = "transfercontract")
    private Set<Bookingtransfer> bookingtransfers = new LinkedHashSet<>();

}
