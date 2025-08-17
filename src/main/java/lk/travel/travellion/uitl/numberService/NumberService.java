package lk.travel.travellion.uitl.numberService;

import lk.travel.travellion.entity.*;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.*;
import lk.travel.travellion.uitl.numberService.projection.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class responsible for generating unique reference numbers and codes
 * for various entities including employees, suppliers, accommodations, locations,
 * cities, transfer contracts, and generic references within the system.
 * This ensures consistent and unique identifiers based on pre-defined patterns
 * and sequences.
 */
@Service
@RequiredArgsConstructor
public class NumberService {

    private final EmployeeRepository employeeRepository;
    private final SupplierRepository supplierRepository;
    private final AccommodationRepository accommodationRepository;
    private final DistrictRepository districtRepository;
    private final LocationRepository locationRepository;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final TransfercontractRepository transfercontractRepository;
    private final GenericRepository genericRepository;
    private final TourcontractRepository tourcontractRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final CustomerpaymentRepository customerpaymentRepository;
    private final SupplierpaymentRepository supplierpaymentRepository;

    /**
     * Generates a unique employee number based on the current year and the highest employee number
     * previously stored in the system.
     *
     * The employee number is composed of the letter 'E', followed by the last two digits of the
     * current year, and a three-digit sequence number. If there are no existing employee numbers or
     * the current year changes, the sequence number resets to "001".
     *
     * @return a newly generated employee number in the format "EYYXXX", where "YY" represents the
     *         last two digits of the current year, and "XXX" is a three-digit sequence number.
     */
    @Transactional(readOnly = true)
    public String generateEmployeeNumber() {

        List<EmployeeNumbers> numbers = employeeRepository.findAllByOrderByNumberDesc();
        int year = (LocalDate.now().getYear());
        String lastTwoNumbers = (Integer.toString(year)).substring(2, 4);

        if (numbers.isEmpty()) {
            return "E".concat(lastTwoNumbers.concat("001"));
        }
        String lastNumber = numbers.get(0).getNumber();
        String lastYear = lastNumber.substring(1, 3);

        if (!lastYear.equals(lastTwoNumbers)) {
            return "E".concat(lastTwoNumbers.concat("001"));
        }
        int nextNumber = Integer.parseInt(lastNumber.substring(3, 6)) + 1;
        return "E".concat(lastTwoNumbers.concat(String.format("%03d", nextNumber)));
    }

    /**
     * Generates a new Supplier Business Registration (BR) number. The generated number follows
     * a specific format: "BRYYXXXXXX", where "YY" represents the last two digits of the current year,
     * and "XXXXXX" is a sequential number starting from 000001 for a new year.
     *
     * If there are no existing numbers, or if the most recent BR number corresponds to a different year,
     * the method will generate a new registration number starting with "000001" for the current year.
     * Otherwise, it will increment the sequential part of the last recorded BR number.
     *
     * @return the newly generated Supplier BR number in the format "BRYYXXXXXX"
     */
    @Transactional(readOnly = true)
    public String generateSupplierBrNumber() {
        List<SupplierBrNo> numbers = supplierRepository.findAllByOrderByBrnoDesc();
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);
        if (numbers.isEmpty()) {
            return "BR" + lastTwoNumbers + "000001";
        }
        String lastNumber = numbers.get(0).getBrno();
        String lastYear = lastNumber.substring(2, 4);
        if (!lastYear.equals(lastTwoNumbers)) {
            return "BR" + lastTwoNumbers + "000001";
        }
        int nextNumber = Integer.parseInt(lastNumber.substring(4, 10)) + 1;
        return "BR" + lastTwoNumbers + String.format("%06d", nextNumber);
    }

    /**
     * Generates a new accommodation reference number for a supplier based on the supplier's BR number
     * and the latest existing reference number. The reference number is formatted as
     * "ACM_SUP_[supplierBrNo]_[lastTwoDigitsOfYear]_[sequenceNumber]". The sequence number resets to "001"
     * if it's the first reference number for the given year.
     *
     * @param supplierBrNo the unique business registration number of the supplier for which the
     *                     accommodation reference number is being generated
     * @return the newly generated accommodation reference number in a specific format
     * @throws ResourceNotFoundException if no supplier is found with the provided supplier BR number
     */
    @Transactional(readOnly = true)
    public String generateAccommodationReferenceNumber(String supplierBrNo) {
        Supplier supplier = supplierRepository.findByBrno(supplierBrNo)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with brno: " + supplierBrNo));

        AccommodationRefNo number = accommodationRepository.findTopBySupplierIdOrderByIdDesc(supplier.getId());
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);

        // If no previous reference exists, start from "001"
        if (number == null) {
            return String.format("ACM_SUP_%s_%s_001", supplier.getBrno(), lastTwoNumbers);
        }

        String lastReference = number.getReference();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset sequence to 001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("ACM_SUP_%s_%s_001", supplier.getBrno(), lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;

        return String.format("ACM_SUP_%s_%s_%03d", supplier.getBrno(), lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a unique location code for a given city code.
     * The location code is generated based on the city code and
     * the last used code in the database for the city.
     *
     * @param citiCode the code representing the city for which the location code is to be generated
     * @return a newly generated unique location code in the format "LOC_<city_code>_XXX",
     * where XXX is a zero-padded incremented number
     * @throws ResourceNotFoundException if no city is found with the provided city code
     */
    @Transactional(readOnly = true)
    public String generateLocationCode(String citiCode) {

        City city = cityRepository.findByCode(citiCode)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with brno: " + citiCode));
        String locationCodePrefix = String.format("LOC_%s_", city.getCode());

        LocationCode code = locationRepository.findTopByCodeLikeOrderByIdDesc(locationCodePrefix + "%");

        if (code == null) {
            return locationCodePrefix + "001";
        }

        String lastCode = code.getCode();
        String[] parts = lastCode.split("_");

        int nextNumber = 1;
        nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;

        return String.format("LOC_%s_%03d", city.getCode(), nextNumber);
    }

    /**
     * Generates a unique city code based on the provided district code and province code.
     * The city code is generated in the format "CTY_{provinceCode}_{districtCode}_{number}".
     * If there are no previous city codes matching the prefix, the first code will end with "001".
     * Subsequent codes increment the number by 1.
     *
     * @param districtCode the code of the district used to generate the city code
     * @param provinceCode the code of the province used to generate the city code
     * @return the generated unique city code
     * @throws ResourceNotFoundException if the district or province corresponding to the provided codes does not exist
     */
    @Transactional(readOnly = true)
    public String generateCityCode(String districtCode, String provinceCode) {

        District district = districtRepository.findByCode(districtCode)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with code : " + districtCode));
        Province province = provinceRepository.findByCode(provinceCode)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found with code : " + provinceCode));

        String citiCodePrefix = String.format("CTY_%s_%s_", province.getCode(), district.getCode());

        CityCode code = cityRepository.findTopByCodeLikeOrderByCodeDesc(citiCodePrefix + "%");

        if (code == null) {
            return citiCodePrefix + "001";
        }

        String lastCode = code.getCode();
        String[] parts = lastCode.split("_");

        int nextNumber = 1;
        nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;

        return String.format("CTY_%s_%s_%03d", province.getCode(), district.getCode(), nextNumber);
    }

    /**
     * Generates a new transfer contract reference number for the given supplier.
     * The reference number is constructed as a unique identifier based on the supplier's
     * business registration number, the current year, and a sequence number.
     * It ensures uniqueness by tracking the last generated reference for the supplier.
     *
     * @param supplierBrNo the business registration number of the supplier for whom
     *                     the reference number is being generated
     * @return the newly generated transfer contract reference number in the format
     *         "TRF_SUP_{supplierBrNo}_{year}_{sequenceNumber}"
     * @throws ResourceNotFoundException if no supplier is found with the provided
     *                                   business registration number
     */
    @Transactional(readOnly = true)
    public String generateTransferContractReferenceNumber(String supplierBrNo) {
        Supplier supplier = supplierRepository.findByBrno(supplierBrNo)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with brno: " + supplierBrNo));

        TransfercontractRefNo number = transfercontractRepository.findTopBySupplierIdOrderByIdDesc(supplier.getId());
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);

        // If no previous reference exists, start from "001"
        if (number == null) {
            return String.format("TRF_SUP_%s_%s_001", supplier.getBrno(), lastTwoNumbers);
        }

        String lastReference = number.getReference();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset sequence to 001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("TRF_SUP_%s_%s_001", supplier.getBrno(), lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;

        return String.format("TRF_SUP_%s_%s_%03d", supplier.getBrno(), lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a generic reference number based on the latest reference
     * available in the generic repository. The reference number format is:
     * "GNC_YY_XXXX", where:
     * - YY: Last two digits of the current year
     * - XXXX: Sequence number, starting from 0001 each year
     *
     * If no previous reference exists, the sequence starts at "0001".
     * The sequence resets to "0001" when the year changes.
     *
     * @return A string representing the generated generic reference number.
     */
    @Transactional(readOnly = true)
    public String generateGenericReferenceNumber() {
        GenericRefNo number = genericRepository.findTopByOrderByIdDesc();
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);
        // If no previous reference exists, start from "0001"
        if (number == null) {
            return String.format("GNC_%s_0001", lastTwoNumbers);
        }

        String lastReference = number.getReference();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset sequence to 0001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("GNC_%s_0001", lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;
        return String.format("GNC_%s_%04d", lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a new unique tour contract reference number based on the most recent entry
     * in the tour contract repository. The reference number format is "TOUR_YY_NNNN", where:
     * - YY is the last two digits of the current year.
     * - NNNN is the sequence number, starting at 0001 each year.
     * If no previous reference exists, the sequence starts at 0001 for the current year.
     *
     * @return A newly generated tour contract reference number.
     */
    @Transactional(readOnly = true)
    public String generateTourContractReferenceNumber() {
        TourcontractRefNo number = tourcontractRepository.findTopByOrderByIdDesc();
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);
        // If no previous reference exists, start from "0001"
        if (number == null) {
            return String.format("TOUR_%s_0001", lastTwoNumbers);
        }

        String lastReference = number.getReference();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset sequence to 0001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("TOUR_%s_0001", lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;
        return String.format("TOUR_%s_%04d", lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a unique customerdto code based on the current year and the last
     * customerdto code from the database. If no previous code exists, the sequence
     * starts from "0001". The code format is "CUS_YY_SEQUENCE", where YY represents
     * the last two digits of the current year, and SEQUENCE is a zero-padded
     * four-digit sequence number. The sequence resets to "0001" at the start of a
     * new year.
     *
     * @return a formatted string representing the generated customerdto code
     */
    @Transactional(readOnly = true)
    public String generateCustomerCode() {

        CustomerCode code = customerRepository.findTopByOrderByIdDesc();
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);
        // If no previous reference exists, start from "0001"
        if (code == null) {
            return String.format("CUS_%s_0001", lastTwoNumbers);
        }

        String lastReference = code.getCode();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset a sequence to 0001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("CUS_%s_0001", lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;
        return String.format("CUS_%s_%04d", lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a unique bookingdto code based on the current year and the last stored bookingdto code.
     * The bookingdto code is formatted as "BOOK_YY_XXXX", where:
     * - YY is the last two digits of the current year.
     * - XXXX is a zero-padded sequence number starting from 0001.
     *
     * If no prior bookingdto codes exist, or if the year has changed, the sequence resets to 0001.
     *
     * This method uses a read-only transactional context for retrieving the last bookingdto code from the database.
     *
     * @return A unique bookingdto code string in the format "BOOK_YY_XXXX".
     */
    @Transactional(readOnly = true)
    public String generateBookingCode() {
        BookingCode number = bookingRepository.findTopByOrderByIdDesc();
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);
        // If no previous reference exists, start from "0001"
        if (number == null) {
            return String.format("BOOK_%s_0001", lastTwoNumbers);
        }

        String lastReference = number.getCode();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset a sequence to 0001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("BOOK_%s_0001", lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;
        return String.format("BOOK_%s_%04d", lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a unique payment code for a customer based on their customer code and the current year.
     * If there are existing payments for the customer in the current year, the last sequence number
     * is incremented. If no payments exist or the year has changed, the sequence is reset.
     *
     * @param customerCode the unique code identifying the customer for whom the payment code is generated
     * @return a newly generated customer payment code in the format: [customerCode]-PAY_[yy]_[sequence]
     *         where [yy] is the last two digits of the current year and [sequence] is a zero-padded number
     */
    @Transactional(readOnly = true)
    public String generateCustomerPaymentCode(String customerCode) {
        Customer customer = customerRepository.findByCode(customerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + customerCode));

        CustomerpaymentCode number = customerpaymentRepository.findTopByCustomerIdOrderByIdDesc(customer.getId());
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);

        // If no previous reference exists, start from "001"
        if (number == null) {
            return String.format("%s-PAY_%s_001", customer.getCode(), lastTwoNumbers);
        }

        String lastReference = number.getCode();
        String[] parts = lastReference.split("_");
        String lastYear = parts[parts.length - 2];

        // If year has changed, reset sequence to 001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("%s-PAY_%s_001", customer.getCode(), lastTwoNumbers);
        }

        // Increment last sequence number
        int nextNumber = Integer.parseInt(parts[parts.length - 1]) + 1;

        return String.format("%s-PAY_%s_%03d", customer.getCode(), lastTwoNumbers, nextNumber);
    }

    /**
     * Generates a unique supplier payment code based on the supplier's brno code and the latest payment record.
     * The generated code format is "PAY_YY_XXX-BRNO" where:
     * - YY represents the last two digits of the current year.
     * - XXX is a sequence number, starting from 001 for each year.
     * - BRNO corresponds to the supplier's brno.
     *
     * If there are no existing payment records or the code format is invalid, it starts the sequence from "001".
     *
     * @param supplierCode The unique brno code of the supplier for which the payment code is being generated.
     * @return A string representing the newly generated supplier payment code.
     * @throws ResourceNotFoundException If no supplier is found for the given brno.
     */
    @Transactional(readOnly = true)
    public String generateSupplierPaymentCode(String supplierCode) {
        Supplier supplier = supplierRepository.findByBrno(supplierCode)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with brno: " + supplierCode));

        SupplierpaymentCode number = supplierpaymentRepository.findTopBySupplierIdOrderByIdDesc(supplier.getId());
        int year = LocalDate.now().getYear();
        String lastTwoNumbers = String.valueOf(year).substring(2, 4);

        // If no previous reference exists, start from "001"
        if (number == null) {
            return String.format("PAY_%s_001-%s", lastTwoNumbers, supplier.getBrno());
        }

        String lastReference = number.getCode();
        // Expected format: PAY_25_001-BR25000003
        String[] parts = lastReference.split("-");

        if (parts.length < 2) {
            // Invalid format, start fresh
            return String.format("PAY_%s_001-%s", lastTwoNumbers, supplier.getBrno());
        }

        String paymentPart = parts[0]; // This should be "PAY_25_001"

        // Split the payment part by underscore to get year and sequence
        String[] paymentParts = paymentPart.split("_");

        if (paymentParts.length < 3) {
            // Invalid format, start fresh
            return String.format("PAY_%s_001-%s", lastTwoNumbers, supplier.getBrno());
        }

        String lastYear = paymentParts[1]; // This should be "25"
        String sequenceNumber = paymentParts[2]; // This should be "001"

        // If year has changed, reset sequence to 001
        if (!lastYear.equals(lastTwoNumbers)) {
            return String.format("PAY_%s_001-%s", lastTwoNumbers, supplier.getBrno());
        }

        // Increment last sequence number
        try {
            int nextNumber = Integer.parseInt(sequenceNumber) + 1;
            return String.format("PAY_%s_%03d-%s", lastTwoNumbers, nextNumber, supplier.getBrno());
        } catch (NumberFormatException e) {
            // If parsing fails, start fresh
            return String.format("PAY_%s_001-%s", lastTwoNumbers, supplier.getBrno());
        }
    }

}
