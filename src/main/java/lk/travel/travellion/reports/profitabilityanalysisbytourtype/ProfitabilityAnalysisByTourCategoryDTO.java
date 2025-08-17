package lk.travel.travellion.reports.profitabilityanalysisbytourtype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfitabilityAnalysisByTourCategoryDTO {

    String tourCategory;
    BigDecimal profit;
}
