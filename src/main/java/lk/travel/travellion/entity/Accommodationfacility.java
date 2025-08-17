package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accommodationfacilities", indexes = {
        @Index(name = "fk_accommodationroom_has_roomfacilities_accommodationroom1_idx", columnList = "accommodationroom_id"),
        @Index(name = "fk_accommodationroom_has_roomfacilities_roomfacilities1_idx", columnList = "roomfacilities_id")
})
public class Accommodationfacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodationroom_id", nullable = false)
    private Accommodationroom accommodationroom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roomfacilities_id", nullable = false)
    private Roomfacility roomfacilities;

}
