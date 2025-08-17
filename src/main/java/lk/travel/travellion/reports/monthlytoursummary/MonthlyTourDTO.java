package lk.travel.travellion.reports.monthlytoursummary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyTourDTO {

    String month;
    Long totalTourCount;
    Long pendingTourCount;

}
