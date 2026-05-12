//package com.cts.claim_service.controller;
//
//
//import com.cts.claim_service.model.ClaimValidation;
//import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import com.cts.claim_service.api.APIResponse;
//import com.cts.claim_service.dto.ClaimRequestDTO;
//import com.cts.claim_service.dto.ClaimResponseDTO;
//import com.cts.claim_service.mapper.ClaimMapper;
//import com.cts.claim_service.model.Claim;
//import com.cts.claim_service.service.ClaimService;
////import com.cts.security.CurrentUserUtil;
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//import com.cts.claim_service.exception.ResourceNotFoundException;
//
//@RestController
//@RequestMapping("/api/claims")
//public class ClaimController {
//
//    private final ClaimService claimService;
//    private final ClaimMapper claimMapper;
//   // private final CurrentUserUtil currentUserUtil;
//
////    public ClaimController(ClaimService claimService, ClaimMapper claimMapper, CurrentUserUtil currentUserUtil) {
////        this.claimService = claimService;
////        this.claimMapper = claimMapper;
////       // this.currentUserUtil = currentUserUtil;
////    }
//public ClaimController(ClaimService claimService, ClaimMapper claimMapper) {
//    this.claimService = claimService;
//    this.claimMapper = claimMapper;
//    // this.currentUserUtil = currentUserUtil;
//}
//
//    @PostMapping
//    //@PreAuthorize("hasRole('CITIZEN')")
//    public ResponseEntity<APIResponse<ClaimResponseDTO>> createClaim(@Valid @RequestBody ClaimRequestDTO dto) {
//        Long citizenId = 1L;//currentUserUtil.getCurrentUserId();
//        Claim claim = claimMapper.toEntity(dto);
//        Claim saved = claimService.createClaim(citizenId, claim);
//        ClaimResponseDTO responseDTO = claimMapper.toDto(saved);
//        APIResponse<ClaimResponseDTO> response = APIResponse.<ClaimResponseDTO>builder()
//                .status("SUCCESS").message("Claim created successfully").data(responseDTO).build();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{claimId}")
//    //@PreAuthorize("hasRole('CITIZEN') or hasRole('OFFICER')")
//    public ResponseEntity<APIResponse<ClaimResponseDTO>> getClaim(@PathVariable Long claimId) throws ResourceNotFoundException {
//        Claim claim = claimService.getClaimById(claimId);
//        ClaimResponseDTO responseDTO = claimMapper.toDto(claim);
//        APIResponse<ClaimResponseDTO> response = APIResponse.<ClaimResponseDTO>builder()
//                .status("SUCCESS").message("Claim retrieved").data(responseDTO).build();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    //@PreAuthorize("hasRole('CITIZEN')")
//    public ResponseEntity<APIResponse<List<ClaimResponseDTO>>> getMyClaims() {
//        Long citizenId = 1L;//currentUserUtil.getCurrentUserId();
//        List<Claim> claims = claimService.getClaimsByCitizen(citizenId);
//        List<ClaimResponseDTO> dtos = claims.stream().map(claimMapper::toDto).collect(Collectors.toList());
//        APIResponse<List<ClaimResponseDTO>> response = APIResponse.<List<ClaimResponseDTO>>builder()
//                .status("SUCCESS").message("Claims retrieved").data(dtos).build();
//        return ResponseEntity.ok(response);
//    }
//
//    @PatchMapping("/{claimId}/status")
//    //@PreAuthorize("hasRole('OFFICER')")
//    public ResponseEntity<APIResponse<ClaimResponseDTO>> updateClaimStatus(@PathVariable Long claimId, @RequestParam Claim.ClaimStatus status) throws ResourceNotFoundException {
//        Claim updated = claimService.updateClaimStatus(claimId, status);
//        ClaimResponseDTO responseDTO = claimMapper.toDto(updated);
//        APIResponse<ClaimResponseDTO> response = APIResponse.<ClaimResponseDTO>builder()
//                .status("SUCCESS").message("Claim status updated").data(responseDTO).build();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/validations")
//    public ResponseEntity<APIResponse<List<ClaimValidation>>> getValidation(){
//
//         List<ClaimValidation> validations=claimService.getClaimsValidations();
//        APIResponse<List<ClaimValidation>> response = APIResponse.<List<ClaimValidation>>builder()
//                .status("SUCCESS").message("Claim Validation fetched").data(validations).build();
//        return ResponseEntity.ok(response);
//
//    }
//}


package com.cts.claim_service.controller;


import com.cts.claim_service.model.ClaimValidation;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cts.claim_service.api.APIResponse;
import com.cts.claim_service.dto.ClaimRequestDTO;
import com.cts.claim_service.dto.ClaimResponseDTO;
import com.cts.claim_service.model.Claim;
import com.cts.claim_service.service.ClaimService;
//import com.cts.security.CurrentUserUtil;
import jakarta.validation.Valid;
import java.util.List;
import com.cts.claim_service.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;
    // private final CurrentUserUtil currentUserUtil;

    //    public ClaimController(ClaimService claimService, ClaimMapper claimMapper, CurrentUserUtil currentUserUtil) {
//        this.claimService = claimService;
//        this.claimMapper = claimMapper;
//       // this.currentUserUtil = currentUserUtil;
//    }
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
        // this.currentUserUtil = currentUserUtil;
    }

    @PostMapping
    //@PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<APIResponse<ClaimResponseDTO>> createClaim(@Valid @RequestBody ClaimRequestDTO dto) {
        Long citizenId = 1L; //currentUserUtil.getCurrentUserId();
        ClaimResponseDTO responseDTO = claimService.createClaim(citizenId, dto);
        APIResponse<ClaimResponseDTO> response = APIResponse.<ClaimResponseDTO>builder()
                .status("SUCCESS").message("Claim created successfully").data(responseDTO).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{claimId}")
    //@PreAuthorize("hasRole('CITIZEN') or hasRole('OFFICER')")
    public ResponseEntity<APIResponse<ClaimResponseDTO>> getClaim(@PathVariable Long claimId) throws ResourceNotFoundException {
        ClaimResponseDTO responseDTO = claimService.getClaimById(claimId);
        APIResponse<ClaimResponseDTO> response = APIResponse.<ClaimResponseDTO>builder()
                .status("SUCCESS").message("Claim retrieved").data(responseDTO).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    //@PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<APIResponse<List<ClaimResponseDTO>>> getMyClaims() {
        Long citizenId = 1L; //currentUserUtil.getCurrentUserId();
        List<ClaimResponseDTO> dtos = claimService.getClaimsByCitizen(citizenId);
        APIResponse<List<ClaimResponseDTO>> response = APIResponse.<List<ClaimResponseDTO>>builder()
                .status("SUCCESS").message("Claims retrieved").data(dtos).build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{claimId}/status")
    //@PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<APIResponse<ClaimResponseDTO>> updateClaimStatus(@PathVariable Long claimId, @RequestParam Claim.ClaimStatus status) throws ResourceNotFoundException {
        ClaimResponseDTO responseDTO = claimService.updateClaimStatus(claimId, status);
        APIResponse<ClaimResponseDTO> response = APIResponse.<ClaimResponseDTO>builder()
                .status("SUCCESS").message("Claim status updated").data(responseDTO).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validations")
    public ResponseEntity<APIResponse<List<ClaimValidation>>> getValidation() {
        List<ClaimValidation> validations = claimService.getClaimsValidations();
        APIResponse<List<ClaimValidation>> response = APIResponse.<List<ClaimValidation>>builder()
                .status("SUCCESS").message("Claim Validation fetched").data(validations).build();
        return ResponseEntity.ok(response);
    }
}