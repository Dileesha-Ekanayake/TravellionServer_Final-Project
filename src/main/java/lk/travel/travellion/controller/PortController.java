package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.locationdto.PortListDTO;
import lk.travel.travellion.dto.locationdto.PortResponseDTO;
import lk.travel.travellion.service.location.PortService;
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
@RequestMapping("/ports")
@RequiredArgsConstructor
public class PortController {

    private final PortService portService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PortResponseDTO>>> getAllPorts() {
        List<PortResponseDTO> portResponseDTOS = portService.getAllPorts();
        return ApiResponse.successResponse(portResponseDTOS);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PortListDTO>>> getPortList() {
        List<PortListDTO> portListDTOS = portService.getAllPortList();
        return ApiResponse.successResponse(portListDTOS);
    }
}
