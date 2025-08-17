package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.RoomfacilityDTO;
import lk.travel.travellion.entity.Roomfacility;
import lk.travel.travellion.service.travelcore.RoomFacilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/roomfacilities")
@RequiredArgsConstructor
public class RoomFacilitiesController {

    private final RoomFacilitiesService roomFacilitiesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RoomfacilityDTO>>> getRoomFacilities() {
        List<RoomfacilityDTO> roomFacilities = roomFacilitiesService.getAllRoomFacilities();
        return ApiResponse.successResponse(roomFacilities);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody RoomfacilityDTO roomfacilityDTO) {
        Roomfacility savedRoomFacility = roomFacilitiesService.saveRoomFacility(roomfacilityDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "RoomFacility : " + savedRoomFacility.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody RoomfacilityDTO roomfacilityDTO) {
        Roomfacility updateRoomFacility = roomFacilitiesService.updateRoomFacility(roomfacilityDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "RoomFacility : " + updateRoomFacility.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        roomFacilitiesService.deleteRoomFacility(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "RoomFacility ID : " + id
        );
    }
}
