package lk.travel.travellion.service.accommodation;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lk.travel.travellion.dto.accommodationdto.AccommodationResponseDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationSearchDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Accommodation;
import lk.travel.travellion.entity.Accommodationroom;
import lk.travel.travellion.entity.Roomtype;
import lk.travel.travellion.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationSearchService {

    private final AccommodationRepository accommodationRepository;
    private final ObjectMapper objectMapper;

    /**
     * Searches for accommodations based on the specified criteria provided in the AccommodationSearchDTO.
     * Filters include name, location, room count, availability based on check-in and check-out dates,
     * and specific room types.
     *
     * @param accommodationSearchDTO the search criteria object containing filters like name, location,
     *                               room count, check-in and check-out dates, and room types.
     * @return a list of AccommodationResponseDTO objects containing the details of the accommodations
     *         that match the search criteria.
     */
    @Transactional(readOnly = true)
    public List<AccommodationResponseDTO> searchAccommodation(AccommodationSearchDTO accommodationSearchDTO) {

        Specification<Accommodation> spec = Specification.where(null);

        // Search using accommodation name
        if (accommodationSearchDTO.getName() != null) {
            spec = spec.and((root, cQuery, cb) ->
                    cb.like(cb.lower(root.get("name")),
                            "%" + accommodationSearchDTO.getName() + "%"));
        }

        // Search using accommodation location
        if (accommodationSearchDTO.getLocation() != null) {
            spec = spec.and((root, cQuery, cb) ->
                    cb.like(cb.lower(root.get("location")),
                            "%" + accommodationSearchDTO.getLocation() + "%"));
        }

        // Search using accommodation's number or rooms
        if (accommodationSearchDTO.getRoomsCount() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                cQuery.distinct(true);
                Join<Accommodation, Accommodationroom> roomJoin = root.join("accommodationrooms", JoinType.INNER);
                return cb.greaterThanOrEqualTo(roomJoin.get("rooms"), accommodationSearchDTO.getRoomsCount());
            });
        }

        // Search using checkIn and checkOut dates match with salesFrom and SalesTo dates
        if (accommodationSearchDTO.getCheckInDate() != null && accommodationSearchDTO.getCheckOutDate() != null) {
            LocalDate checkIn = accommodationSearchDTO.getCheckInDate();
            LocalDate checkOut = accommodationSearchDTO.getCheckOutDate();

            spec = spec.and((root, cQuery, cb) -> cb.and(
                    cb.lessThanOrEqualTo(root.get("salesfrom"), checkIn),
                    cb.greaterThanOrEqualTo(root.get("salesto"), checkOut)
            ));
        }

        // Search using accommodation's room types
        if (accommodationSearchDTO.getRoomTypes() != null) {
//            spec = spec.and((root, cQuery, cb) -> {
////                cQuery.distinct(true);
//                // First join from Accommodation to AccommodationRoom
//                Join<Accommodation, Accommodationroom> roomJoin = root.join("accommodationrooms", JoinType.INNER);
//
//                // Then join from AccommodationRoom to RoomType
//                Join<Accommodationroom, Roomtype> roomTypeJoin = roomJoin.join("roomtype", JoinType.INNER);
//
//                // Check if any of the room types match the requested IDs
//                return roomTypeJoin.get("id").in(accommodationSearchDTO.getRoomTypes());
//
//            });
            spec = spec.and((root, query, cb) -> {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Accommodation> subRoot = subquery.from(Accommodation.class);
                Join<Accommodation, Accommodationroom> roomJoin = subRoot.join("accommodationrooms");
                Join<Accommodationroom, Roomtype> roomTypeJoin = roomJoin.join("roomtype");

                subquery.select(subRoot.get("id"))
                        .where(cb.and(
                                cb.equal(subRoot.get("id"), root.get("id")),
                                roomTypeJoin.get("id").in(accommodationSearchDTO.getRoomTypes())
                        ))
                        .groupBy(subRoot.get("id"))
                        .having(cb.equal(cb.countDistinct(roomTypeJoin.get("id")), accommodationSearchDTO.getRoomTypes().size()));

                return cb.exists(subquery);
            });

        }

        return objectMapper.toAccommodationResponseDTOs(accommodationRepository.findAll(spec));

    }

}
