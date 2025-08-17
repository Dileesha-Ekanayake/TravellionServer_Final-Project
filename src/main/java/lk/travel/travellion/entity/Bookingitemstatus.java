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
@Table(name = "bookingitemstatus")
public class Bookingitemstatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "bookingitemstatus")
    private Set<Bookingaccommodation> bookingaccommodations = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bookingitemstatus")
    private Set<Bookinggeneric> bookinggenerics = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bookingitemstatus")
    private Set<Bookingtour> bookingtours = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bookingitemstatus")
    private Set<Bookingtransfer> bookingtransfers = new LinkedHashSet<>();

}
