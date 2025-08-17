package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.customerdto.RelationshipDTO;
import lk.travel.travellion.service.customer.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customerrelations")
@RequiredArgsConstructor
public class CustomerRelationshipController {

    private final RelationshipService relationshipService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RelationshipDTO>>> getCustomerRelationships() {
        return ApiResponse.successResponse(relationshipService.getAllRelationships());
    }
}
