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

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "supplier", indexes = {
        @Index(name = "fk_supplier_user1_idx", columnList = "user_id"),
        @Index(name = "fk_supplier_suppliertype1_idx", columnList = "suppliertype_id"),
        @Index(name = "fk_supplier_supplierstatus1_idx", columnList = "supplierstatus_id")
})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 10)
    @NotNull
    @Unique
    @Column(name = "brno", length = 10)
    private String brno;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "photo")
    private byte[] photo;

    @Size(max = 10)
    @Column(name = "mobile", length = 10)
    private String mobile;

    @Size(max = 10)
    @Column(name = "land", length = 10)
    private String land;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 100)
    @Column(name = "state", length = 100)
    private String state;

    @Size(max = 50)
    @Column(name = "country", length = 50)
    private String country;

    @Size(max = 10)
    @Column(name = "zipcode", length = 10)
    private String zipcode;

    @Size(max = 45)
    @Column(name = "bank_account", length = 45)
    private String bankAccount;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "createdon", updatable = false)
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "suppliertype_id", nullable = false)
    private Suppliertype suppliertype;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "supplierstatus_id", nullable = false)
    private Supplierstatus supplierstatus;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Set<Accommodation> accommodations = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Set<Transfercontract> transfercontracts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Set<Generic> generics = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Set<Supplierpayment> supplierpayments = new LinkedHashSet<>();

}
