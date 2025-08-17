package lk.travel.travellion.reports.profitabilityanalysisbytourtype;

import lk.travel.travellion.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfitabilityAnalysisByToursService {

    private final BookingRepository bookingRepository;

    /**
     * Retrieves a list of profitability analysis data categorized by tour type.
     *
     * @return a list of {@code ProfitabilityAnalysisByTourTypeDTO} containing the tour type and corresponding profit data.
     */
    public List<ProfitabilityAnalysisByTourTypeDTO> getProfitabilityAnalysisByTourType(){
        return bookingRepository.getProfitabilityAnalysisByTourType();
    }

    /**
     * Retrieves a list of profitability analysis data categorized by tour category.
     *
     * @return a list of {@link ProfitabilityAnalysisByTourCategoryDTO} containing tour category names
     *         and their corresponding profits. If no data is found, returns an empty list.
     */
    public List<ProfitabilityAnalysisByTourCategoryDTO> getProfitabilityAnalysisByTourCategory(){
        return bookingRepository.getProfitabilityAnalysisByTourCategory();
    }

    /**
     * Retrieves a profitability analysis grouped by tour themes.
     *
     * @return a list of {@code ProfitabilityAnalysisByTourThemeDTO} objects, each representing
     *         the tour theme and its associated profitability. If no data is available,
     *         an empty list is returned.
     */
    public List<ProfitabilityAnalysisByTourThemeDTO> getProfitabilityAnalysisByTourTheme(){
        return bookingRepository.getProfitabilityAnalysisByTourTheme();
    }

    /**
     * Retrieves the total number of tour bookings and their associated revenue details.
     *
     * @return an instance of TotalTourBookingsAndRevenueDTO containing the total bookings,
     *         total revenue amount, and the supplier amount.
     */
    public TotalTourBookingsAndRevenueDTO getTotalTourBookingsAndRevenue(){
        return bookingRepository.getTotalTourBookingsAndRevenue();
    }
}
