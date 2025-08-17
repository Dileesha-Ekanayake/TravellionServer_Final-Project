package lk.travel.travellion.reports.paymentcollection;

import lk.travel.travellion.repository.CustomerpaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCollectionDataService {

    private final CustomerpaymentRepository customerpaymentRepository;

    /**
     * Retrieves a list of payment collection details within the specified date range, grouped by month.
     * Each entry contains details like total received payments, pending payments, overdue amounts,
     * and the collection rate for the corresponding month.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate the end date of the range (inclusive)
     * @return a list of {@code PaymentCollectionDTO} containing payment collection data
     */
    public List<PaymentCollectionDTO> getPaymentCollectionByDateRange(LocalDate startDate, LocalDate endDate) {
        List<PaymentCollectionDTO> monthlyDataList = new ArrayList<>();

        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate rangeEnd = endDate.withDayOfMonth(endDate.lengthOfMonth());

        while (!currentMonth.isAfter(rangeEnd)) {
            LocalDate monthStart = currentMonth.withDayOfMonth(1);
            LocalDate monthEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth());

            // Restrict the range within start and end dates
            LocalDate actualStart = monthStart.isBefore(startDate) ? startDate : monthStart;
            LocalDate actualEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;

            Timestamp startTimestamp = Timestamp.valueOf(actualStart.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(actualEnd.atTime(23, 59, 59));

            List<Object[]> result = customerpaymentRepository.getCustomerPaymentCollection(startTimestamp, endTimestamp);

            for (Object[] row : result) {
                int year = ((Number) row[0]).intValue();
                int month = ((Number) row[1]).intValue();
                String monthName = (String) row[2];

                BigDecimal totalReceived = safeValue(row[3]);
                BigDecimal pendingPayments = safeValue(row[4]);
                BigDecimal overdueAmount = safeValue(row[5]);
                BigDecimal collectionRate = calculateCollectionRate(totalReceived, pendingPayments);

                monthlyDataList.add(new PaymentCollectionDTO(
                        year,
                        month,
                        monthName,
                        totalReceived,
                        pendingPayments,
                        overdueAmount,
                        collectionRate
                ));
            }

            currentMonth = currentMonth.plusMonths(1);
        }
        System.out.println(monthlyDataList);
        return monthlyDataList;
    }

    /**
     * Retrieves a list of payment collections within the specified date range.
     * Converts the provided date strings to LocalDate objects and delegates processing
     * to another method that operates on LocalDate types.
     *
     * @param startDateStr the start date of the range, in string format (e.g., "yyyy-MM-dd")
     * @param endDateStr the end date of the range, in string format (e.g., "yyyy-MM-dd")
     * @return a list of PaymentCollectionDTO objects representing the payment collection data within the date range
     */
    public List<PaymentCollectionDTO> getPaymentCollectionByDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getPaymentCollectionByDateRange(startDate, endDate);
    }

    /**
     * Converts an object to a BigDecimal. If the input value is null, it returns BigDecimal.ZERO.
     *
     * @param value the input object to be converted to BigDecimal, can be null
     * @return the converted BigDecimal value or BigDecimal.ZERO if the input is null
     */
    private BigDecimal safeValue(Object value) {
        return value != null ? new BigDecimal(value.toString()) : BigDecimal.ZERO;
    }

    /**
     * Calculates the collection rate as a percentage based on the total received payments
     * and the total pending payments.
     *
     * @param totalReceived the total amount received
     * @param pendingPayments the total amount of pending payments
     * @return the collection rate as a percentage, rounded to two decimal places.
     *         Returns 0% if the total expected payments (sum of totalReceived and pendingPayments) is 0.
     */
    private BigDecimal calculateCollectionRate(BigDecimal totalReceived, BigDecimal pendingPayments) {
        BigDecimal totalExpected = totalReceived.add(pendingPayments);
        if (totalExpected.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return totalReceived
                .divide(totalExpected, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}

