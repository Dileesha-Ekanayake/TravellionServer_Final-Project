package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@Table(name = "user", indexes = {
        @Index(name = "fk_user_employee1_idx", columnList = "employee_id"),
        @Index(name = "fk_user_userstatus1_idx", columnList = "userstatus_id"),
        @Index(name = "fk_user_usertype1_idx", columnList = "usertype_id1")
})
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Size(max = 45)
    @Column(name = "username", length = 45, unique = true, nullable = false)
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Column(name = "createdon", updatable = false)
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @Lob
    @Column(name = "descrption")
    private String descrption;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userstatus_id", nullable = false)
    private Userstatus userstatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usertype_id", nullable = false)
    private Usertype usertype;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Userrole> userroles = new LinkedHashSet<>();

    @Size(max = 6)
    @Column(name = "recovery_code", length = 6)
    private String recoveryCode;

    @Column(name = "recovery_code_expiration")
    private Timestamp recoveryCodeExpiration;

    @Column(name = "recovery_code_used")
    private Boolean recoveryCodeUsed;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Supplier> suppliers = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Accommodation> accommodations = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Location> locations = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Transfercontract> transfercontracts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Generic> generics = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Tourcontract> tourcontracts = new LinkedHashSet<>();

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Customer> customers = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Customerpayment> customerpayments = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Supplierpayment> supplierpayments = new LinkedHashSet<>();

}
