package lk.travel.travellion.service.generic;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lk.travel.travellion.dto.genericdto.GenericResponseDTO;
import lk.travel.travellion.dto.genericdto.GenericSearchDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Generic;
import lk.travel.travellion.entity.Generictype;
import lk.travel.travellion.repository.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericSearchService {

    private final GenericRepository genericRepository;
    private final ObjectMapper objectMapper;

    /**
     * Searches for Generic entities based on the provided search criteria and returns a list of matching results.
     *
     * @param genericSearchDTO the criteria used to filter the search. It includes name, salesfrom, salesto,
     *                         and generictype fields to refine the results.
     * @return a list of GenericResponseDTO, where each object represents a matched Generic entity based
     *         on the specified search criteria.
     */
    public List<GenericResponseDTO> searchGeneric(GenericSearchDTO genericSearchDTO) {

        Specification<Generic> spec = Specification.where(null);

        if (genericSearchDTO.getName() != null) {
            spec = spec.and((root, cQuery, cb) ->
                    cb.like(root.get("name"), "%" + genericSearchDTO.getName() + "%"));
        }

        if (genericSearchDTO.getGenerictype() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Generic, Generictype> genericTypeJoin = root.join("generictype", JoinType.INNER);
                return cb.equal(genericTypeJoin.get("name"), genericSearchDTO.getGenerictype().toLowerCase());
            });
        }

        if (genericSearchDTO.getSalesfrom() != null && genericSearchDTO.getSalesto() != null) {
            LocalDate salesFrom = genericSearchDTO.getSalesfrom();
            LocalDate salesTo = genericSearchDTO.getSalesto();

            spec = spec.and((root, cQuery, cb) -> cb.and(
                    cb.lessThanOrEqualTo(root.get("salesfrom"), salesFrom),
                    cb.greaterThanOrEqualTo(root.get("salesto"), salesTo)
            ));
        }

        return objectMapper.toGenericResponseDTOs(genericRepository.findAll(spec));
    }
}
