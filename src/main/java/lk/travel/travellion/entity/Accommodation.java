package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
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
@Table(name = "accommodation", indexes = {
        @Index(name = "fk_accommodation_user1_idx", columnList = "user_id"),
        @Index(name = "fk_accommodation_supplier1_idx", columnList = "supplier_id"),
        @Index(name = "fk_accommodation_accommodationstatus1_idx", columnList = "accommodationstatus_id"),
        @Index(name = "fk_accommodation_residenttype1_idx", columnList = "residenttype_id"),
        @Index(name = "fk_accommodation_currency1_idx", columnList = "currency_id"),
})
public class Accommodation {
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

    @Size(max = 45)
    @NotNull
    @Unique
    @Column(name = "reference", nullable = false, length = 45)
    private String reference;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "location", length = 100)
    private String location;

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

    @Column(name = "createdon")
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodationstatus_id", nullable = false)
    private Accommodationstatus accommodationstatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "residenttype_id", nullable = false)
    private Residenttype residenttype;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodationcncelationcharge> accommodationcncelationcharges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodationdiscount> accommodationdiscounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodationroom> accommodationrooms = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation")
    private Set<Touraccommodation> touraccommodations = new LinkedHashSet<>();

    @NotNull
//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodationtype_id", nullable = false)
    private Accommodationtype accommodationtype;

    @NotNull
//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "starrating_id", nullable = false)
    private Starrating starrating;

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation")
    private Set<Bookingaccommodation> bookingaccommodations = new LinkedHashSet<>();

}
