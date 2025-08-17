package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "privilege", indexes = {
        @Index(name = "fk_privilege_role1_idx", columnList = "role_id"),
        @Index(name = "fk_privilege_module1_idx", columnList = "module_id"),
        @Index(name = "fk_privilege_operation1_idx", columnList = "operation_id")
})
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @Size(max = 255)
    @Column(name = "authority")
    private String authority;

}
