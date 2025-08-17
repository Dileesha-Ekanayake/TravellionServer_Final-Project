package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.PaxtypeDTO;
import lk.travel.travellion.entity.Paxtype;
import lk.travel.travellion.service.travelcore.PaxTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/paxtypes")
@RequiredArgsConstructor
public class PaxTypeController {

    private final PaxTypeService paxTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PaxtypeDTO>>> getPaxTypes() {
        List<PaxtypeDTO> paxTypes = paxTypeService.getAllPaxTypes();
        return ApiResponse.successResponse(paxTypes);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody PaxtypeDTO paxtypeDTO) {
        Paxtype savedPaxType = paxTypeService.savePaxType(paxtypeDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "PaxType : " + savedPaxType.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody PaxtypeDTO paxtypeDTO) {
        Paxtype updatePaxType = paxTypeService.updatePaxType(paxtypeDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "PaxType : " + updatePaxType.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        paxTypeService.deletePaxType(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "PaxType ID : " + id
        );
    }
}
