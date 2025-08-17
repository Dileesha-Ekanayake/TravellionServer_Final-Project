package lk.travel.travellion.reports.monthlytoursummary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalTourDataDTO {

    BigDecimal totalTourCount;
    BigDecimal pendingTourCount;

}
