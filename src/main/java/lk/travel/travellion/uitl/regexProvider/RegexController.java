package lk.travel.travellion.uitl.regexProvider;

import lk.travel.travellion.dto.accommodationdto.AccommodationRequestDTO;
import lk.travel.travellion.dto.bookingdto.BookingRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerContactRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerIdentityRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerRequestDTO;
import lk.travel.travellion.dto.employeedto.EmployeeRequestDTO;
import lk.travel.travellion.dto.genericdto.GenericRequestDTO;
import lk.travel.travellion.dto.locationdto.AirportRequestDTO;
import lk.travel.travellion.dto.locationdto.CityRequestDTO;
import lk.travel.travellion.dto.locationdto.LocationRequestDTO;
import lk.travel.travellion.dto.locationdto.PortRequestDTO;
import lk.travel.travellion.dto.supplierdto.SupplierRequestDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractRequestDTO;
import lk.travel.travellion.dto.userdto.UserRequestDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * The RegexController class provides endpoints for retrieving predefined regular expressions
 * based on different request types. Each endpoint is associated with a specific entity type
 * and returns a HashMap of regex definitions for validating data related to that entity.
 *
 * Annotations:
 * - @RestController: Indicates that this class is a REST controller.
 * - @CrossOrigin: Allows cross-origin requests.
 * - @RequestMapping: Maps HTTP requests to the base path "/regexes".
 *
 * Endpoints:
 * - /employee: Returns regexes for validating Employee-related data.
 * - /user: Returns regexes for validating User-related data.
 * - /supplier: Returns regexes for validating Supplier-related data.
 * - /accommodation: Returns regexes for validating Accommodation-related data.
 * - /city: Returns regexes for validating City-related data.
 * - /airport: Returns regexes for validating Airport-related data.
 * - /port: Returns regexes for validating Port-related data.
 * - /location: Returns regexes for validating Location-related data.
 * - /generic: Returns generic regexes for common validations.
 * - /tourcontract: Returns regexes for validating Tour Contract-related data.
 * - /customer: Returns regexes for validating Customer-related data.
 * - /customerIdentity: Returns regexes for validating Customer Identity-related data.
 * - /customerContact: Returns regexes for validating Customer Contact-related data.
 * - /booking: Returns regexes for validating Booking-related data.
 *
 * Each method is mapped to a specific path and produces responses in JSON format (MediaType.APPLICATION_JSON_VALUE).
 * The methods utilize the RegexProvider class to retrieve the corresponding regexes for the specified entity type.
 */
@RestController
@CrossOrigin
@RequestMapping("/regexes")
public class RegexController {

    @GetMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> employee() {
        return RegexProvider.get(new EmployeeRequestDTO());
    }

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> user() {
        return RegexProvider.get(new UserRequestDTO());
    }

    @GetMapping(path = "/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> supplier() {
        return RegexProvider.get(new SupplierRequestDTO());
    }

    @GetMapping(path = "/accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> accommodation() {
        return RegexProvider.get(new AccommodationRequestDTO());
    }

    @GetMapping(path = "/city", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> city() {
        return RegexProvider.get(new CityRequestDTO());
    }

    @GetMapping(path = "/airport", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> airport() {
        return RegexProvider.get(new AirportRequestDTO());
    }

    @GetMapping(path = "/port", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> port() {
        return RegexProvider.get(new PortRequestDTO());
    }

    @GetMapping(path = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> location() {
        return RegexProvider.get(new LocationRequestDTO());
    }

    @GetMapping(path = "/generic", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> generic() {
        return RegexProvider.get(new GenericRequestDTO());
    }

    @GetMapping(path = "/tourcontract", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> tourContract() {
        return RegexProvider.get(new TourcontractRequestDTO());
    }

    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> customer() {
        return RegexProvider.get(new CustomerRequestDTO());
    }

    @GetMapping(path = "/customerIdentity", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> customerIdentity() {
        return RegexProvider.get(new CustomerIdentityRequestDTO());
    }

    @GetMapping(path = "/customerContact", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> customerContact() {
        return RegexProvider.get(new CustomerContactRequestDTO());
    }

    @GetMapping(path = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, HashMap<String, String>> booking() {
        return RegexProvider.get(new BookingRequestDTO());
    }
}
