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
@Table(name = "tourcontract")
public class Tourcontract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

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

    @Column(name = "maxpaxcount")
    private Integer maxpaxcount;

    @Column(name = "createdon", updatable = false)
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @OneToMany(mappedBy = "tourcontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Touraccommodation> touraccommodations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tourcontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tourgeneric> tourgenerics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tourcontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tourtransfercontract> tourtransfercontracts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tourcontract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Touroccupancy> touroccupancies = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tourtype_id", nullable = false)
    private Tourtype tourtype;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tourcategory_id", nullable = false)
    private Tourcategory tourcategory;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tourtheme_id", nullable = false)
    private Tourtheme tourtheme;

    @JsonIgnore
    @OneToMany(mappedBy = "tourcontract")
    private Set<Bookingtour> bookingtours = new LinkedHashSet<>();

}
