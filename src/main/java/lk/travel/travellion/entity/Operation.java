package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "operation", indexes = {
        @Index(name = "fk_operation_module1_idx", columnList = "module_id")
})
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "display_name", length = 45)
    private String displayName;

    @Size(max = 45)
    @Column(name = "operation", length = 45)
    private String operation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;

    @JsonIgnore
    @OneToMany(mappedBy = "operation")
    private Set<Privilege> privileges = new LinkedHashSet<>();

}
