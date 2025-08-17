package lk.travel.travellion.dtomapper;

import lk.travel.travellion.dto.accommodationdto.*;
import lk.travel.travellion.dto.bookingdto.*;
import lk.travel.travellion.dto.customerdto.CustomerListDTO;
import lk.travel.travellion.dto.customerdto.CustomerRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerResponseDTO;
import lk.travel.travellion.dto.customerdto.RelationshipDTO;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentRequestDTO;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentResponseDTO;
import lk.travel.travellion.dto.customerpaymentdto.PaymenttypeDTO;
import lk.travel.travellion.dto.designationdto.DesignationDTO;
import lk.travel.travellion.dto.employeedto.*;
import lk.travel.travellion.dto.genderdto.GenderDTO;
import lk.travel.travellion.dto.genericdto.*;
import lk.travel.travellion.dto.locationdto.*;
import lk.travel.travellion.dto.moduledto.ModuleDTO;
import lk.travel.travellion.dto.operationdto.OperationDTO;
import lk.travel.travellion.dto.privilegedto.PrivilegeDTO;
import lk.travel.travellion.dto.setupdetailsdto.*;
import lk.travel.travellion.dto.supplierdto.*;
import lk.travel.travellion.dto.supplierpaymentdto.PaymentstatusDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentInfoDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentRequestDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentResponseDTO;
import lk.travel.travellion.dto.tourcontractdto.*;
import lk.travel.travellion.dto.transfercontractdto.*;
import lk.travel.travellion.dto.userdto.*;
import lk.travel.travellion.entity.*;
import lk.travel.travellion.entity.Module;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * ObjectMapper interface defines the contract for mapping objects between various DTOs and entities.
 * It is designed to convert entities into data transfer objects and vice versa, facilitating interaction
 * between the API layer and the service or database layer. This interface uses a Spring component model
 * to integrate with Spring's dependency injection.
 *
 * This mapper handles a wide variety of objects including Employees, Genders, Designations, Users,
 * Operations, Privileges, Roles, Modules, Suppliers, Accommodation details, Locations, Transfer,
 * Generic, Tour Contracts, Customer details, Bookings, and Payments.
 *
 * Methods are categorized by their domain for better organization:
 *
 * - Employee: Maps employee entities and DTOs.
 * - Gender: Maps gender entities and DTOs.
 * - Designation: Maps designation entities and DTOs.
 * - EmployeeStatus: Maps employee status entities and DTOs.
 * - EmployeeType: Maps employee type entities and DTOs.
 * - User: Maps user entities and DTOs.
 * - UserStatus: Maps user status entities and DTOs.
 * - UserType: Maps user type entities and DTOs.
 * - Operation: Maps operation entities and DTOs.
 * - Privilege: Maps privilege entities and DTOs.
 * - Role: Maps role entities and DTOs.
 * - Module: Maps module entities and DTOs.
 * - Supplier: Maps supplier entities and DTOs.
 * - SupplierStatus: Maps supplier status entities and DTOs.
 * - SupplierType: Maps supplier type entities and DTOs.
 * - SetupDetails: Maps room, rate, resident, cancellation, and currency entities and DTOs.
 * - Accommodations: Maps accommodation and related entities and DTOs.
 * - Location: Maps location, city, airport, province, and district entities and DTOs.
 * - TransferContract: Maps transfer-related contracts and details.
 * - Generic: Provides generic mappings for common entities and DTOs.
 * - TourContract: Maps tour contract and related details into entities and DTOs.
 * - Customer: Maps customers, relationships, and related details.
 * - Booking: Maps booking and associated status or information DTOs.
 * - Customer Payment: Maps customer payment entities and related DTOs.
 * - Supplier Payment: Maps supplier payment entities and their associated DTOs.
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper {


    //=============================Employee=============================//
    List<EmployeeListDTO> toEmployeeListDTOs(List<Employee> employees);

    EmployeeforUserDetailsDTO toEmployeeForUserDetailsDTO(Employee employee);

    List<EmployeeResponseDTO> toEmployeeResponseDTO(List<Employee> employee);
    Employee toEmployeeEntity(EmployeeRequestDTO employeeRequestDTO);

    //=============================Gender=============================//
    List<GenderDTO> toGenderDTOs(List<Gender> genders);

    //=============================Designation=============================//
    List<DesignationDTO> toDesignationDTOs(List<Designation> designations);

    //=============================EmployeeStatus=============================//
    List<EmployeestatusDTO> toEmployeeStatusDTOs(List<Employeestatus> employeestatuss);

    //=============================EmployeeType=============================//
    List<EmployeetypeDTO> toEmployeeTypeDTOs(List<Employeetype> employeetypes);

    //=============================User=============================//
    List<UserResponseDTO> toUserResponseDTOs(List<User> users);
//    List<UserResponseDTO> toUserResponseDTO(List<User> users);

    List<UserListDTO> toUserListDTOs(List<User> users);

    User toUserEntity(UserRequestDTO userRequestDTO);

    //=============================UserStatus=============================//
    List<UserstatusDTO> toUserStatusDTOs(List<Userstatus> userstatuss);

    //=============================UserType=============================//
    List<UsertypeDTO> toUserTypeDTOs(List<Usertype> usertypes);

    //=============================Operation=============================//
    List<OperationDTO> toOperationDTOs(List<Operation> operations);
    OperationDTO toOperationDTO(Operation operation);

    Operation toOperationEntity(OperationDTO operationDTO);

    //=============================Privilege=============================//
    List<PrivilegeDTO> toPrivilegeDTOs(List<Privilege> privileges);

    Privilege toPrivilegeEntity(PrivilegeDTO privilegeDTO);

    //=============================Role=============================//
    List<RoleDTO> toRoleDTOs(List<Role> roles);

    //=============================Module=============================//
    List<ModuleDTO> toModuleDTOs(List<Module> modules);

    //=============================Supplier=============================//
    List<SupplierResponseDTO> toSupplierResponseDTOs(List<Supplier> suppliers);
    List<SupplierListDTO> toSupplierListDTOs(List<Supplier> suppliers);

    Supplier toSupplierEntity(SupplierRequestDTO supplierRequestDTO);

    //=============================SupplierStatus=============================//
    List<SupplierstatusDTO> toSupplierStatusDTOs(List<Supplierstatus> supplierstatus);

    //=============================SupplierType=============================//
    List<SuppliertypeDTO> toSupplierTypeDTOs(List<Suppliertype> suppliertypes);

    //=============================SetupDetails=============================//
    List<RoomtypeDTO> toRoomTypeDTOs(List<Roomtype> roomtypes);
    List<RoomfacilityDTO> toRoomFacilityDTOs(List<Roomfacility> roomfacilities);
    List<PaxtypeDTO> toPaxTypeDTOs(List<Paxtype> paxtypes);
    List<CancellationschemeDTO> toCancellationSchemeDTOs(List<Cancellationscheme> cancellationschemes);
    List<RatetypeDTO> toRateTypeDTOs(List<Ratetype> ratetypes);
    List<ResidenttypeDTO> toResidentTypeDTOs(List<Residenttype> residenttypes);
    List<CurrencyDTO> toCurrencyDTOs(List<Currency> currencies);

    Roomtype toRoomTypeEntity(RoomtypeDTO roomtypeDTO);
    Roomfacility toRoomFacilityEntity(RoomfacilityDTO roomfacilityDTO);
    Paxtype toPaxTypeEntity(PaxtypeDTO paxtypeDTO);
    Cancellationscheme toCancellationSchemeEntity(CancellationschemeDTO cancelationschemeDTO);
    Ratetype toRateTypeEntity(RatetypeDTO rateTypeDTO);
    Residenttype toResidentTypeEntity(ResidenttypeDTO residenttypeDTO);
    Currency toCurrencyEntity(CurrencyDTO currencyDTO);

    //=============================Accommodations=============================//
    List<AccommodationstatusDTO> toAccommodationstatusDTOs(List<Accommodationstatus> accommodationstatus);
    List<AccommodationdiscounttypeDTO> toAccommodationdiscounttypeDTOs(List<Accommodationdiscounttype> accommodationdiscounttype);
    List<AccommodationtypeDTO> toAccommodationtypeDTOs(List<Accommodationtype> accommodationtype);
    List<StarratingDTO> toStarratingDTOs(List<Starrating> starrating);
    List<AccommodationdiscountResponseDTO> toAccommodationdiscountResponseDTOs(List<Accommodationdiscount> accommodationdiscount);
    List<AccommodationResponseDTO> toAccommodationResponseDTOs(List<Accommodation> accommodations);
    Accommodation toAccommodationEntity(AccommodationRequestDTO accommodationRequestDTO);

    //=============================Location=============================//
    List<LocationResponseDTO> toLocationResponseDTOs(List<Location> locations);
    Location toLocationEntity(LocationRequestDTO locationRequestDTO);
    List<ProvinceResponseDTO> toProvinceResponseDTOs(List<Province> provinces);
    List<DistrictResponseDTO> toDistrictResponseDTOs(List<District> districts);
    List<AirportResponseDTO> toAirportResponseDTOs(List<Airport> airports);
    List<PortResponseDTO> toPortResponseDTOs(List<Port> ports);
    List<CityResponseDTO> toCityResponseDTOs(List<City> cities);
    City toCityEntity(CityRequestDTO cityRequestDTO);
    List<CityListDTO> toCityListDTOs(List<City> cities);
    List<LocationListDTO> toLocationListDTOs(List<Location> locations);
    List<AirportListDTO> toAirportListDTOs(List<Airport> airports);
    List<PortListDTO> toPortListDTOs(List<Port> ports);

    //=============================TransferContract=============================//
    List<TransferstatusDTO> toTransferStatusDTOs(List<Transferstatus> transferstatuses);
    List<TransferdiscounttypeDTO> toTransferDiscountTypeDTOs(List<Transferdiscounttype> transferdiscounttypes);
    List<TransfertypeDTO> toTransferTypeDTOs(List<Transfertype> transfertypes);
    List<LocationtypeDTO> toLocationTypeDTOs(List<Locationtype> locationtypes);

    List<TransfercontractResponseDTO> toTransferContractResponseDTOs(List<Transfercontract> transfercontracts);
    Transfercontract toTransferContractEntity(TransfercontractRequestDTO transfercontractRequestDTO);

    //=============================Generic=============================//
    List<GenericstatusDTO> toGenericStatusDTOs(List<Genericstatus> genericstatus);
    List<GenericdiscounttypeDTO> toGenericDiscountTypeDTOs(List<Genericdiscounttype> genericdiscounttypes);
    List<GenerictypeDTO> toGenericTypeDTOs(List<Generictype> generictypes);
    List<GenericResponseDTO> toGenericResponseDTOs(List<Generic> generics);
    Generic toGenericEntity(GenericRequestDTO genericRequestDTO);

    //=============================TourContract=============================//
    List<TourcontractResponseDTO> toTourcontractResponseDTOs(List<Tourcontract> tourcontracts);
    Tourcontract toTourcontractEntity(TourcontractRequestDTO tourcontractRequestDTO);
    List<TourtypeDTO> toTourTypeDTOs(List<Tourtype> tourtypes);
    List<TourcategoryDTO> toTourCategoryDTOs(List<Tourcategory> tourcategories);
    List<TourthemeDTO> toTourThemeDTOs(List<Tourtheme> tourthemes);
    TourcontractViewResponseDTO toTourcontractViewResponseDTO (Tourcontract tourcontract);

    //==============================Customer================================//
    List<CustomerResponseDTO> toCustomerResponseDTOs(List<Customer> customers);
    Customer toCustomerEntity(CustomerRequestDTO customerRequestDTO);
    List<RelationshipDTO> toRelationshipDTOs(List<Relationship> relationships);
    List<CustomerListDTO> toCustomerListDTOs(List<Customer> customers);

    //==============================Booking=================================//
    List<BookingstatusDTO> toBookingStatusDTOs(List<Bookingstatus> bookingstatuses);
    List<BookingitemstatusDTO> toBookingItemStatusDTOs(List<Bookingitemstatus> bookingitemstatuses);
    List<BookingResponseDTO> toBookingResponseDTOs(List<Booking> bookings);
    Booking toBookingEntity(BookingRequestDTO bookingRequestDTO);
    List<BookingListDTO> toBookingListDTOs(List<Booking> bookings);
    BookingBalanceDTO toBookingBalanceDTO (Booking booking);
    BookingViewResponseDTO toBookingViewResponseDTO (Booking booking);

    //=============================customer payment==========================//
    List<PaymenttypeDTO> toPaymentTypeDTOs(List<Paymenttype> paymenttypes);
    List<CustomerpaymentResponseDTO> toCustomerpaymentResponseDTOs(List<Customerpayment> customerpayments);
    Customerpayment toCustomerpaymentEntity(CustomerpaymentRequestDTO customerpaymentRequestDTO);

    //=============================supplier payment==========================//
    List<PaymentstatusDTO> toPaymentStatusDTOs(List<Paymentstatus> paymentstatuses);
    List<SupplierpaymentResponseDTO> toSupplierpaymentResponseDTOs(List<Supplierpayment> supplierpayments);
    Supplierpayment toSupplierpaymentEntity(SupplierpaymentRequestDTO supplierpaymentRequestDTO);
    SupplierpaymentInfoDTO toSupplierpaymentInfoDTO (Supplierpayment supplierpayment);
}
