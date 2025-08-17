package lk.travel.travellion.service.customer;

import lk.travel.travellion.dto.customerdto.RelationshipDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<RelationshipDTO> getAllRelationships() {
        return objectMapper.toRelationshipDTOs(relationshipRepository.findAll());
    }

}
