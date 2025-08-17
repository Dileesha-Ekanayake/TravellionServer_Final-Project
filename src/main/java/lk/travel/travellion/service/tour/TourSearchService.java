package lk.travel.travellion.service.tour;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lk.travel.travellion.dto.tourcontractdto.TourcontractResponseDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractSearchDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Tourcategory;
import lk.travel.travellion.entity.Tourcontract;
import lk.travel.travellion.entity.Tourtheme;
import lk.travel.travellion.entity.Tourtype;
import lk.travel.travellion.repository.TourcontractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourSearchService {

    private final TourcontractRepository tourcontractRepository;
    private final ObjectMapper objectMapper;

    /**
     * Searches for tours based on various criteria provided in the TourcontractSearchDTO parameter.
     * The search includes filtering by tour name, sales date range, tour type, tour category,
     * tour theme, and number of passengers (pax count).
     *
     * @param tourcontractSearchDTO an object containing the search criteria:
     *                               - name: the name of the tour to search for (partial match, case-insensitive).
     *                               - salesfrom and salesto: the sales date range to filter tours.
     *                               - tourtype: the type of the tour.
     *                               - tourcategory: the category of the tour.
     *                               - tourtheme: the theme of the tour.
     *                               - paxcount: the minimum number of passengers for the tour.
     * @return a list of TourcontractResponseDTO objects that match the given search criteria.
     */
    @Transactional(readOnly = true)
    public List<TourcontractResponseDTO> searchTour(TourcontractSearchDTO tourcontractSearchDTO) {

        Specification<Tourcontract> spec = Specification.where(null);

        // Search using tour name
        if (tourcontractSearchDTO.getName() != null) {
            spec = spec.and((root, cQuery, cb) ->
                    cb.like(cb.lower(root.get("name")),
                            "%" + tourcontractSearchDTO.getName() + "%"));
        }

        // Search using checkIn and checkOut dates match with salesFrom and SalesTo dates
        if (tourcontractSearchDTO.getSalesfrom() != null && tourcontractSearchDTO.getSalesto() != null) {
            LocalDate salesFrom = tourcontractSearchDTO.getSalesfrom();
            LocalDate salesTo = tourcontractSearchDTO.getSalesto();

            spec = spec.and((root, cQuery, cb) -> cb.and(
                    cb.lessThanOrEqualTo(root.get("salesfrom"), salesFrom),
                    cb.greaterThanOrEqualTo(root.get("salesto"), salesTo)
            ));
        }

        if (tourcontractSearchDTO.getTourtype() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Tourcontract, Tourtype> tourTypeTypeJoin = root.join("tourtype", JoinType.INNER);
                return cb.equal(tourTypeTypeJoin.get("name"), tourcontractSearchDTO.getTourtype().toLowerCase());
            });
        }

        if (tourcontractSearchDTO.getTourcategory() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Tourcontract, Tourcategory> tourCategoryTypeJoin = root.join("tourcategory", JoinType.INNER);
                return cb.equal(tourCategoryTypeJoin.get("name"), tourcontractSearchDTO.getTourcategory().toLowerCase());
            });
        }

        if (tourcontractSearchDTO.getTourtheme() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Tourcontract, Tourtheme> tourThemeTypeJoin = root.join("tourtheme", JoinType.INNER);
                return cb.equal(tourThemeTypeJoin.get("name"), tourcontractSearchDTO.getTourtheme().toLowerCase());
            });
        }

        if (tourcontractSearchDTO.getPaxcount() != null) {
            Integer paxCount = tourcontractSearchDTO.getPaxcount();

            spec = spec.and((root, cQuery, cb) -> {
                return switch (paxCount) {
                    case 2 -> cb.greaterThanOrEqualTo(root.get("maxpaxcount"), 2);
                    case 3 -> cb.greaterThanOrEqualTo(root.get("maxpaxcount"), 3);
                    case 4 -> cb.greaterThanOrEqualTo(root.get("maxpaxcount"), 4);
                    case 10 -> cb.greaterThanOrEqualTo(root.get("maxpaxcount"), 10);
                    default -> cb.greaterThanOrEqualTo(root.get("maxpaxcount"), 1);
                };
            });
        }

        return objectMapper.toTourcontractResponseDTOs(tourcontractRepository.findAll(spec));
    }

}
