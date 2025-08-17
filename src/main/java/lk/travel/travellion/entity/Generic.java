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
@Table(name = "generic")
public class Generic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "markup", precision = 10, scale = 2)
    private BigDecimal markup;

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

    @Column(name = "createdon", updatable = false)
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "generictype_id", nullable = false)
    private Generictype generictype;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "genericstatus_id", nullable = false)
    private Genericstatus genericstatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @OneToMany(mappedBy = "generic", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Genericrate> genericrates = new LinkedHashSet<>();

    @OneToMany(mappedBy = "generic", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Genericcancellationcharge> genericcancellationcharges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "generic", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Genericdiscount> genericdiscounts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "generic")
    private Set<Tourgeneric> tourgenerics = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "generic")
    private Set<Bookinggeneric> bookinggenerics = new LinkedHashSet<>();

}
