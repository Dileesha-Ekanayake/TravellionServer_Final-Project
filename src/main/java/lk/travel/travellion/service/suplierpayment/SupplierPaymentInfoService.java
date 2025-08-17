package lk.travel.travellion.service.suplierpayment;


import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentInfoDTO;
import lk.travel.travellion.entity.Supplier;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.BookingRepository;
import lk.travel.travellion.repository.SupplierRepository;
import lk.travel.travellion.repository.SupplierpaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SupplierPaymentInfoService {

    private final BookingRepository bookingRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierpaymentRepository supplierpaymentRepository;

    /**
     * Retrieves the payment details for a supplier identified by their unique business
     * registration number (BRN). The method calculates the total amount to be paid, the
     * previously paid amount, and the remaining balance for the specified supplier.
     *
     * @param supplierBrno The business registration number (BRN) of the supplier for
     *                     whom payment details are to be retrieved.
     * @return A {@link SupplierpaymentInfoDTO} object containing the supplier's payment
     *         details, including the total amount to be paid, previously paid amount,
     *         and remaining balance.
     * @throws ResourceNotFoundException if no supplier is found with the provided BRN.
     */
    protected SupplierpaymentInfoDTO getSupplierPaymentDetails(String supplierBrno) {

        BigDecimal totalAmountToBePaidForDirectBooking = BigDecimal.ZERO;
        BigDecimal totalAmountToBePaidForTourBooking = BigDecimal.ZERO;
        BigDecimal previousTotalPaidAmount = BigDecimal.ZERO;

        BigDecimal totalPreviousAmountToBePaid = BigDecimal.ZERO;

        Supplier supplier = supplierRepository.findByBrno(supplierBrno)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with brno: " + supplierBrno));

        // Get the previously paid amount
        previousTotalPaidAmount = supplierpaymentRepository.sumAmountBySupplierBrno(supplierBrno);

        // Handle a null case for sum operation
        if (previousTotalPaidAmount == null) {
            previousTotalPaidAmount = BigDecimal.ZERO;
        }

        String supplierType = supplier.getSuppliertype().getName();

        switch (supplierType) {
            case "Accommodations":
                totalAmountToBePaidForDirectBooking = bookingRepository.findTotalAccommodationSupplierAmount(supplierBrno);
                totalAmountToBePaidForTourBooking = bookingRepository.findTotalAccommodationSupplierAmountByTour(supplierBrno);
                break;
            case "Transfer":
                totalAmountToBePaidForDirectBooking = bookingRepository.findTotalTransferSupplierAmount(supplierBrno);
                totalAmountToBePaidForTourBooking = bookingRepository.findTotalTransferSupplierAmountByTour(supplierBrno);
                break;
            case "Generic":
                totalAmountToBePaidForDirectBooking = bookingRepository.findTotalGenericSupplierAmount(supplierBrno);
                totalAmountToBePaidForTourBooking = bookingRepository.findTotalGenericSupplierAmountByTour(supplierBrno);
                break;
        }

        // Handle null cases for booking amounts
        if (totalAmountToBePaidForDirectBooking == null) {
            totalAmountToBePaidForDirectBooking = BigDecimal.ZERO;
        }
        if (totalAmountToBePaidForTourBooking == null) {
            totalAmountToBePaidForTourBooking = BigDecimal.ZERO;
        }

        // Calculate the total amount to be paid
        BigDecimal totalAmountToBePaid = totalAmountToBePaidForDirectBooking.add(totalAmountToBePaidForTourBooking);

        // Calculate balance (remaining amount to be paid)
        BigDecimal balance = totalAmountToBePaid.subtract(previousTotalPaidAmount);

        // Create and return the DTO
        return new SupplierpaymentInfoDTO(
                supplier.getId(),
                supplierBrno, // Using supplier brno as code
                totalAmountToBePaid,
                previousTotalPaidAmount,
                balance
        );
    }
}
