package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roomfacilities")
public class Roomfacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "roomfacilities")
    private Set<Accommodationfacility> accommodationfacilities = new LinkedHashSet<>();

}
