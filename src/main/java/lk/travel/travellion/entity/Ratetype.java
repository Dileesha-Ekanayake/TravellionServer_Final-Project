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
@Table(name = "ratetype")
public class Ratetype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "ratetype")
    private Set<Accommodationcncelationcharge> accommodationcncelationcharges = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ratetype")
    private Set<Transfercancellationcharge> transfercancellationcharges = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ratetype")
    private Set<Transferdiscount> transferdiscounts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ratetype")
    private Set<Genericcancellationcharge> genericcancellationcharges = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ratetype")
    private Set<Genericdiscount> genericdiscounts = new LinkedHashSet<>();

}
