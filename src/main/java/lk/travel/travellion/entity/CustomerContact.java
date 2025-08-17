package lk.travel.travellion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer_contact")
public class CustomerContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Column(name = "mobile", length = 10)
    private String mobile;

    @Size(max = 10)
    @Column(name = "land", length = 10)
    private String land;

    @Size(max = 10)
    @Column(name = "emergency_contact_number", length = 10)
    private String emergencyContactNumber;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100)
    @Column(name = "address_line1", length = 100)
    private String addressLine1;

    @Size(max = 100)
    @Column(name = "address_line2", length = 100)
    private String addressLine2;

    @Size(max = 45)
    @Column(name = "postal_code", length = 45)
    private String postalCode;

    @Size(max = 45)
    @Column(name = "country", length = 45)
    private String country;

    @Size(max = 45)
    @Column(name = "city", length = 45)
    private String city;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
