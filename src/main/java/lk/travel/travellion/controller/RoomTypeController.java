package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.RoomtypeDTO;
import lk.travel.travellion.entity.Roomtype;
import lk.travel.travellion.service.travelcore.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/roomtypes")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RoomtypeDTO>>> getRoomTypes() {
        List<RoomtypeDTO> roomtypes = roomTypeService.getAllRoomTypes();
        return ApiResponse.successResponse(roomtypes);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody RoomtypeDTO roomtypeDTO) {
        Roomtype savedRoomType = roomTypeService.saveRoomType(roomtypeDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "RoomType : " + savedRoomType.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody RoomtypeDTO roomtypeDTO) {
        Roomtype updateRoomType = roomTypeService.updateRoomType(roomtypeDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "RoomType : " + updateRoomType.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        roomTypeService.deleteRoomType(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "RoomType ID : " + id
        );
    }
}
