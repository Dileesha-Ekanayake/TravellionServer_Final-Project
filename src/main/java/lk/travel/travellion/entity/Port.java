package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

@Getter
@Setter
@Entity
@Table(name = "port", indexes = {
        @Index(name = "fk_port_city1_idx", columnList = "city_id")
})
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 6)
    @Column(name = "code", length = 6)
    private String code;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "photo")
    private byte[] photo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}
