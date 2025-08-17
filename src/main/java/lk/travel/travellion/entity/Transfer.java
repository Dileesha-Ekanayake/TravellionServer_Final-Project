package lk.travel.travellion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "isreturn")
    private Boolean isreturn;

    @OneToMany(mappedBy = "transfer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Droplocation> droplocations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "transfer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pickuplocation> pickuplocations = new LinkedHashSet<>();

//    @JsonIgnore
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transfercontract> transfercontracts = new LinkedHashSet<>();

}
