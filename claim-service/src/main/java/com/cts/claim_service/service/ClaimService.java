//package com.cts.claim_service.service;
//import com.cts.claim_service.exception.ResourceNotFoundException;
//import com.cts.claim_service.model.Claim;
//import com.cts.claim_service.model.ClaimValidation;
//
//import java.util.List;
//
//public interface ClaimService {
//    Claim createClaim(Long citizenId, Claim claim);
//    Claim getClaimById(Long claimId) throws ResourceNotFoundException;
//    List<Claim> getClaimsByCitizen(Long citizenId);
//    Claim updateClaimStatus(Long claimId, Claim.ClaimStatus status) throws ResourceNotFoundException;
//    List<ClaimValidation> getClaimsValidations();
//}

package com.cts.claim_service.service;

import com.cts.claim_service.dto.ClaimRequestDTO;
import com.cts.claim_service.dto.ClaimResponseDTO;
import com.cts.claim_service.model.Claim;
import com.cts.claim_service.model.ClaimValidation;
import com.cts.claim_service.exception.ResourceNotFoundException;
import java.util.List;

public interface ClaimService {
    ClaimResponseDTO createClaim(Long citizenId, ClaimRequestDTO dto);
    ClaimResponseDTO getClaimById(Long claimId) throws ResourceNotFoundException;
    List<ClaimResponseDTO> getClaimsByCitizen(Long citizenId);
    ClaimResponseDTO updateClaimStatus(Long claimId, Claim.ClaimStatus status) throws ResourceNotFoundException;
    List<ClaimValidation> getClaimsValidations();
}
