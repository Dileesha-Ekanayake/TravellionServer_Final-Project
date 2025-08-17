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
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<Privilege> privileges = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Userrole> userroles = new LinkedHashSet<>();

}
