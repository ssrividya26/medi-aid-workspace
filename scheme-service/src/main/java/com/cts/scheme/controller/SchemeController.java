//package com.cts.scheme.controller;
//
//
//import com.cts.scheme.dto.SchemeRequestDTO;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import com.cts.scheme.api.APIResponse;
//import com.cts.scheme.dto.SchemeResponseDTO;
//import com.cts.scheme.mapper.SchemeMapper;
//import com.cts.scheme.model.Scheme;
//import com.cts.scheme.service.SchemeService;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/schemes")
//public class SchemeController {
//
//    private final SchemeService schemeService;
//    private final SchemeMapper schemeMapper;
//
//    public SchemeController(SchemeService schemeService, SchemeMapper schemeMapper) {
//        this.schemeService = schemeService;
//        this.schemeMapper = schemeMapper;
//    }
//
//    @GetMapping
//    //@PreAuthorize("hasRole('CITIZEN') or hasRole('OFFICER') or hasRole('MANAGER')")
//    public ResponseEntity<APIResponse<List<SchemeResponseDTO>>> getAllSchemes() {
//        List<Scheme> schemes = schemeService.getAllSchemes();
//        List<SchemeResponseDTO> dtos = schemes.stream().map(schemeMapper::toDto).collect(Collectors.toList());
//        APIResponse<List<SchemeResponseDTO>> response = APIResponse.<List<SchemeResponseDTO>>builder()
//                .status("SUCCESS").message("Schemes retrieved").data(dtos).build();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{schemeId}")
//   // @PreAuthorize("hasRole('CITIZEN') or hasRole('OFFICER') or hasRole('MANAGER')")
//    public ResponseEntity<APIResponse<SchemeResponseDTO>> getScheme(@PathVariable Long schemeId) {
//        Scheme scheme = schemeService.getSchemeById(schemeId);
//        SchemeResponseDTO responseDTO = schemeMapper.toDto(scheme);
//        APIResponse<SchemeResponseDTO> response = APIResponse.<SchemeResponseDTO>builder()
//                .status("SUCCESS").message("Scheme retrieved").data(responseDTO).build();
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping
//    public ResponseEntity<APIResponse<SchemeResponseDTO>> createScheme(@Valid @RequestBody SchemeRequestDTO dto){
//        Scheme scheme=schemeMapper.toEntity(dto);
//        Scheme saved=schemeService.createScheme(scheme);
//
//        SchemeResponseDTO responseDTO=schemeMapper.toDto(saved);
//
//        APIResponse<SchemeResponseDTO> response=new APIResponse<>("Success","Scheme created successfully ",responseDTO);
//        return ResponseEntity.ok(response);
//
//    }
//}




package com.cts.scheme.controller;


import com.cts.scheme.dto.SchemeRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cts.scheme.api.APIResponse;
import com.cts.scheme.dto.SchemeResponseDTO;
import com.cts.scheme.service.SchemeService;
import java.util.List;

@RestController
@RequestMapping("/api/schemes")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('CITIZEN') or hasRole('OFFICER') or hasRole('MANAGER')")
    public ResponseEntity<APIResponse<List<SchemeResponseDTO>>> getAllSchemes() {
        List<SchemeResponseDTO> dtos = schemeService.getAllSchemes();
        APIResponse<List<SchemeResponseDTO>> response = APIResponse.<List<SchemeResponseDTO>>builder()
                .status("SUCCESS").message("Schemes retrieved").data(dtos).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{schemeId}")
    // @PreAuthorize("hasRole('CITIZEN') or hasRole('OFFICER') or hasRole('MANAGER')")
    public ResponseEntity<APIResponse<SchemeResponseDTO>> getScheme(@PathVariable Long schemeId) {
        SchemeResponseDTO responseDTO = schemeService.getSchemeById(schemeId);
        APIResponse<SchemeResponseDTO> response = APIResponse.<SchemeResponseDTO>builder()
                .status("SUCCESS").message("Scheme retrieved").data(responseDTO).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<SchemeResponseDTO>> createScheme(@Valid @RequestBody SchemeRequestDTO dto) {
        SchemeResponseDTO responseDTO = schemeService.createScheme(dto);
        APIResponse<SchemeResponseDTO> response = new APIResponse<>("Success", "Scheme created successfully", responseDTO);
        return ResponseEntity.ok(response);
    }
}