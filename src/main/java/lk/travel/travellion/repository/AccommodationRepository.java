package lk.travel.travellion.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Accommodation;
import lk.travel.travellion.entity.Supplier;
import lk.travel.travellion.service.booking.RoomCountDTO;
import lk.travel.travellion.uitl.numberService.projection.AccommodationRefNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer>, JpaSpecificationExecutor<Accommodation> {
    boolean existsByReference(@Size(max = 45) @NotNull String reference);

    boolean existsByReferenceAndIdNot(@Size(max = 45) @NotNull String reference, Integer id);

    AccommodationRefNo findTopBySupplierIdOrderByIdDesc(Integer supplier_id);

    @Query("""
            SELECT NEW lk.travel.travellion.service.booking.RoomCountDTO(
                accmRooms.roomtype.name,
                accmRooms.rooms
            )
            FROM Accommodation accmm
                JOIN accmm.accommodationrooms accmRooms
            WHERE accmm.id = :accommId
              AND accmRooms.roomtype.name IN :roomTypes
    """)
    List<RoomCountDTO> getRoomCountsByAccommodationIdAndRoomTypes(
            @Param("accommId") Integer accommId,
            @Param("roomTypes") List<String> roomTypes
    );
}
