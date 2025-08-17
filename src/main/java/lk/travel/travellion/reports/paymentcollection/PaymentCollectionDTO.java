package lk.travel.travellion.reports.paymentcollection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentCollectionDTO {

    private int year;
    private int month;
    private String monthName;
    private BigDecimal totalReceivedPayments;
    private BigDecimal totalPendingPayments;
    private BigDecimal totalOverduePayments;
    private BigDecimal collectionRate;
}
