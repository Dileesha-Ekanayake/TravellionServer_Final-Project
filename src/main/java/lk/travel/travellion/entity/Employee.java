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
@Table(name = "employee", indexes = {
        @Index(name = "fk_employee_gender_idx", columnList = "gender_id"),
        @Index(name = "fk_employee_designation1_idx", columnList = "designation_id"),
        @Index(name = "fk_employee_employeetype1_idx", columnList = "employeetype_id"),
        @Index(name = "fk_employee_employeestatus1_idx", columnList = "employeestatus_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "number_UNIQUE", columnNames = {"number"})
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 6)
    @NotNull
    @Unique
    @Column(name = "number", length = 6)
    private String number;

    @Size(max = 255)
    @Column(name = "fullname")
    private String fullname;

    @Size(max = 45)
    @Column(name = "callingname", length = 45)
    private String callingname;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "dobirth")
    private LocalDate dobirth;

    @Size(max = 12)
    @Column(name = "nic", length = 12)
    private String nic;

    @Lob
    @Column(name = "address")
    private String address;

    @Size(max = 10)
    @Column(name = "mobile", length = 10)
    private String mobile;

    @Size(max = 10)
    @Column(name = "land", length = 10)
    private String land;

    @Size(max = 45)
    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "doassignment")
    private LocalDate doassignment;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "createdon", updatable = false)
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "updatedon")
    @UpdateTimestamp
    private Timestamp updatedon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "designation_id", nullable = false)
    private Designation designation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employeetype_id", nullable = false)
    private Employeetype employeetype;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employeestatus_id", nullable = false)
    private Employeestatus employeestatus;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private Set<User> users = new LinkedHashSet<>();

}
