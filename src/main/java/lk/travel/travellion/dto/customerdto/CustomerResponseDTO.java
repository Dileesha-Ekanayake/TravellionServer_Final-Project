package lk.travel.travellion.dto.customerdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Customer}
 */
@Value
public class CustomerResponseDTO implements Serializable {
    Integer id;
    @Size(max = 11)
    String code;
    @Size(max = 100)
    String fullname;
    @Size(max = 45)
    String callingname;
    LocalDate dobirth;
    String description;
    @NotNull
    UserDTO user;
    @NotNull
    Residenttype residenttype;
    Set<CustomerContactDto> customerContacts;
    Set<CustomerIdentityDto> customerIdentities;
    Set<PassengerDto> passengers;
    Timestamp createdon;
    Timestamp updatedon;
    @NotNull
    Gender gender;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDTO implements Serializable {
        Integer id;
        String username;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.CustomerContact}
     */
    @Value
    public static class CustomerContactDto implements Serializable {
        Integer id;
        String mobile;
        String land;
        String emergencyContactNumber;
        String email;
        String addressLine1;
        String addressLine2;
        String postalCode;
        String country;
        String city;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.CustomerIdentity}
     */
    @Value
    public static class CustomerIdentityDto implements Serializable {
        Integer id;
        String nic;
        String passportNo;
        String passportExpiryDate;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Passenger}
     */
    @Value
    public static class PassengerDto implements Serializable {
        Integer id;
        String code;
        String name;
        Integer age;
        Relationship relationship;
        Residenttype residenttype;
        Paxtype paxtype;
    }
}
