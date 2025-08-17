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
@Table(name = "paxtype")
public class Paxtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "paxtype")
    private Set<Accommodationoccupanciespax> accommodationoccupanciespaxes = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paxtype")
    private Set<Accommodationrate> accommodationrates = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paxtype")
    private Set<Transferrate> transferrates = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paxtype")
    private Set<Genericrate> genericrates = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paxtype")
    private Set<Passenger> passengers = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paxtype")
    private Set<Touroccupancy> touroccupancies = new LinkedHashSet<>();

}
