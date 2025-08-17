package lk.travel.travellion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "district", indexes = {
        @Index(name = "fk_distric_province1_idx", columnList = "province_id")
})
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 6)
    @Column(name = "code", length = 6)
    private String code;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private Set<City> cities = new LinkedHashSet<>();

}
