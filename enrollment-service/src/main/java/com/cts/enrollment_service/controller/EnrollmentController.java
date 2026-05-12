//package com.cts.enrollment_service.controller;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.cts.enrollment_service.api.APIResponse;
//import com.cts.enrollment_service.dto.EnrollmentRequestDTO;
//import com.cts.enrollment_service.dto.EnrollmentResponseDTO;
//import com.cts.enrollment_service.mapper.EnrollmentMapper;
//import com.cts.enrollment_service.model.Enrollment;
//import com.cts.enrollment_service.service.EnrollmentService;
//
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/enrollments")
//public class EnrollmentController {
//
//    private final EnrollmentService enrollmentService;
//    private final EnrollmentMapper enrollmentMapper;
//
//    public EnrollmentController(EnrollmentService enrollmentService,
//                                EnrollmentMapper enrollmentMapper) {
//        this.enrollmentService = enrollmentService;
//        this.enrollmentMapper = enrollmentMapper;
//    }
//
//    @PostMapping
//    public ResponseEntity<APIResponse<EnrollmentResponseDTO>> createEnrollment(
//            @Valid @RequestBody EnrollmentRequestDTO dto) {
//
//
//        Long citizenId = 1L;
//
//        Enrollment enrollment = enrollmentMapper.toEntity(dto);
//
//        Enrollment saved = enrollmentService.createEnrollment(citizenId, enrollment);
//
//        EnrollmentResponseDTO responseDTO = enrollmentMapper.toDto(saved);
//
//        APIResponse<EnrollmentResponseDTO> response =
//                APIResponse.<EnrollmentResponseDTO>builder()
//                        .status("SUCCESS")
//                        .message("Enrollment created successfully")
//                        .data(responseDTO)
//                        .build();
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<APIResponse<EnrollmentResponseDTO>> getEnrollment(@PathVariable Long id) {
//
//        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
//
//        EnrollmentResponseDTO dto = enrollmentMapper.toDto(enrollment);
//
//        return ResponseEntity.ok(
//                APIResponse.<EnrollmentResponseDTO>builder()
//                        .status("SUCCESS")
//                        .message("Enrollment retrieved")
//                        .data(dto)
//                        .build()
//        );
//    }
//
//    @GetMapping
//    public ResponseEntity<APIResponse<List<EnrollmentResponseDTO>>> getAll() {
//
//        Long citizenId = 1L;
//
//        List<Enrollment> list = enrollmentService.getEnrollmentsByCitizen(citizenId);
//
//        List<EnrollmentResponseDTO> dtos =
//                list.stream().map(enrollmentMapper::toDto).collect(Collectors.toList());
//
//        return ResponseEntity.ok(
//                APIResponse.<List<EnrollmentResponseDTO>>builder()
//                        .status("SUCCESS")
//                        .message("Enrollments retrieved")
//                        .data(dtos)
//                        .build()
//        );
//    }
//
//    @PatchMapping ("/{id}/status")
//    public ResponseEntity<APIResponse<EnrollmentResponseDTO>> updateStatus(
//            @PathVariable Long id,
//            @RequestParam Enrollment.EnrollmentStatus status) {
//
//        Enrollment updated = enrollmentService.updateEnrollmentStatus(id, status);
//
//        return ResponseEntity.ok(
//                APIResponse.<EnrollmentResponseDTO>builder()
//                        .status("SUCCESS")
//                        .message("Status updated")
//                        .data(enrollmentMapper.toDto(updated))
//                        .build()
//        );
//    }
//
//    @GetMapping("/validate")
//    public Boolean validateEnrollment(@RequestParam Long citizenId,@RequestParam Long schemeId){
//        return enrollmentService.existsEnrollment(citizenId,schemeId);
//    }
//}

package com.cts.enrollment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cts.enrollment_service.api.APIResponse;
import com.cts.enrollment_service.dto.EnrollmentRequestDTO;
import com.cts.enrollment_service.dto.EnrollmentResponseDTO;
import com.cts.enrollment_service.model.Enrollment;
import com.cts.enrollment_service.service.EnrollmentService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<EnrollmentResponseDTO>> createEnrollment(
            @Valid @RequestBody EnrollmentRequestDTO dto) {

        Long citizenId = 1L;

        EnrollmentResponseDTO responseDTO = enrollmentService.createEnrollment(citizenId, dto);

        APIResponse<EnrollmentResponseDTO> response =
                APIResponse.<EnrollmentResponseDTO>builder()
                        .status("SUCCESS")
                        .message("Enrollment created successfully")
                        .data(responseDTO)
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<EnrollmentResponseDTO>> getEnrollment(@PathVariable Long id) {

        EnrollmentResponseDTO dto = enrollmentService.getEnrollmentById(id);

        return ResponseEntity.ok(
                APIResponse.<EnrollmentResponseDTO>builder()
                        .status("SUCCESS")
                        .message("Enrollment retrieved")
                        .data(dto)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<EnrollmentResponseDTO>>> getAll() {

        Long citizenId = 1L;

        List<EnrollmentResponseDTO> dtos = enrollmentService.getEnrollmentsByCitizen(citizenId);

        return ResponseEntity.ok(
                APIResponse.<List<EnrollmentResponseDTO>>builder()
                        .status("SUCCESS")
                        .message("Enrollments retrieved")
                        .data(dtos)
                        .build()
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<APIResponse<EnrollmentResponseDTO>> updateStatus(
            @PathVariable Long id,
            @RequestParam Enrollment.EnrollmentStatus status) {

        EnrollmentResponseDTO responseDTO = enrollmentService.updateEnrollmentStatus(id, status);

        return ResponseEntity.ok(
                APIResponse.<EnrollmentResponseDTO>builder()
                        .status("SUCCESS")
                        .message("Status updated")
                        .data(responseDTO)
                        .build()
        );
    }

    @GetMapping("/validate")
    public Boolean validateEnrollment(@RequestParam Long citizenId, @RequestParam Long schemeId) {
        return enrollmentService.existsEnrollment(citizenId, schemeId);
    }
}