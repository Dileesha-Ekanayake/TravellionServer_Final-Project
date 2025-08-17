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
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 11)
    @Unique
    @Column(name = "code", length = 11)
    private String code;

    @Size(max = 100)
    @Column(name = "fullname", length = 100)
    private String fullname;

    @Size(max = 45)
    @Column(name = "callingname", length = 45)
    private String callingname;

    @Column(name = "dobirth")
    private LocalDate dobirth;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "residenttype_id", nullable = false)
    private Residenttype residenttype;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CustomerContact> customerContacts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerIdentity> customerIdentities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Passenger> passengers = new LinkedHashSet<>();

    @Column(name = "createdon")
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Customerpayment> customerpayments = new LinkedHashSet<>();

}
