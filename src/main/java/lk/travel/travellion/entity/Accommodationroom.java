package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accommodationroom", indexes = {
        @Index(name = "fk_accommodation_has_roomtype_accommodation1_idx", columnList = "accommodation_id"),
        @Index(name = "fk_accommodation_has_roomtype_roomtype1_idx", columnList = "roomtype_id")
})
public class Accommodationroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roomtype_id", nullable = false)
    private Roomtype roomtype;

    @Column(name = "rooms")
    private Integer rooms;

    @JsonManagedReference
    @OneToMany(mappedBy = "accommodationroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodationfacility> accommodationfacilities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "accommodationroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodationoccupanciespax> accommodationoccupanciespaxes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "accommodationroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodationrate> accommodationrates = new LinkedHashSet<>();

}
