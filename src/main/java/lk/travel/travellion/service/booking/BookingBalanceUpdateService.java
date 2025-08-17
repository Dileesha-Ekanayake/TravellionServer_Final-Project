package lk.travel.travellion.service.booking;

import lk.travel.travellion.entity.Booking;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.BookingRepository;
import lk.travel.travellion.repository.CustomerpaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingBalanceUpdateService {

    private final BookingRepository bookingRepository;
    private final CustomerpaymentRepository customerpaymentRepository;

    /**
     * Updates the booking balance for the specified booking by recalculating the total paid amount and
     * the remaining balance based on the provided new payment amount. If the total number of payments
     * associated with the booking is less than or equal to one, this method does nothing and returns early.
     *
     * @param bookingCode the unique code identifying the booking for which the balance needs to be updated
     * @param newPaidAmount the new payment amount to be added to the total paid amount of the booking
     */
    public void updateBookingBalance(String bookingCode, BigDecimal newPaidAmount){

        int existingCustomerPayment = customerpaymentRepository.countByBookingCode(bookingCode);

        if (existingCustomerPayment <= 1){
            return;
        }

        Booking booking = bookingRepository.findByCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with code: " + bookingCode));

        BigDecimal previousTotalPaid = booking.getTotalpaid() != null ? booking.getTotalpaid() : BigDecimal.ZERO;
        BigDecimal netAmount = booking.getNetamount() != null ? booking.getNetamount() : BigDecimal.ZERO;

        BigDecimal newTotalPaid = previousTotalPaid.add(newPaidAmount);

        BigDecimal newBalance = netAmount.subtract(newTotalPaid);

        booking.setTotalpaid(newTotalPaid);
        booking.setBalance(newBalance);

        bookingRepository.save(booking);
    }

    /**
     * Updates the payment details for a booking and recalculates the total paid and balance amounts
     * based on the sum of payments associated with the booking code.
     *
     * @param bookingCode the unique identifier for the booking whose payment details need to be updated
     * @throws ResourceNotFoundException if no booking is found with the specified booking code
     */
    public void updateExistingPayment(String bookingCode) {

        Booking existingBooking = bookingRepository.findByCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with code: " + bookingCode));

        BigDecimal sumAmount = customerpaymentRepository.sumAmountByBookingCode(existingBooking.getCode());

        BigDecimal netAmount = existingBooking.getNetamount() != null ? existingBooking.getNetamount() : BigDecimal.ZERO;

        existingBooking.setTotalpaid(sumAmount);
        existingBooking.setBalance(netAmount.subtract(sumAmount));

        bookingRepository.save(existingBooking);
    }
}
