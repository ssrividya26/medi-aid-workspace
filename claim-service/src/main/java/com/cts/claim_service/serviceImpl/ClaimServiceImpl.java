//package com.cts.claim_service.serviceImpl;
//import com.cts.claim_service.client.EnrollmentClient;
//import com.cts.claim_service.model.ClaimValidation;
//import com.cts.claim_service.repository.ClaimValidationRepository;
//import com.cts.claim_service.service.ClaimService;
//import com.cts.claim_service.exception.BadRequestException;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
////import com.cts.aspect.Audited;
//import com.cts.claim_service.exception.ResourceNotFoundException;
//import com.cts.claim_service.model.Claim;
//import com.cts.claim_service.repository.ClaimRepository;
//import jakarta.transaction.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class ClaimServiceImpl implements ClaimService {
//
//    @Autowired
//    private ClaimRepository claimRepository;
//    @Autowired
//    private EnrollmentClient enrollmentClient;
//    @Autowired
//    private ClaimValidationRepository validationRepository;
//
//    @Override
//    //@Audited(action = "CREATE", resource = "Claim")
//    @CircuitBreaker(name="enrollmentService",fallbackMethod = "enrollmentFallback")
//    public Claim createClaim(Long citizenId, Claim claim) {
//
//        Boolean isValid=enrollmentClient.validateEnrollment(citizenId,claim.getSchemeId());
//        if(!isValid){
//            throw new BadRequestException("Citizen is not enrolled in this scheme");
//        }
//
//        claim.setCitizenId(citizenId);
//        claim.setClaimDate(LocalDate.now().toString());
//        claim.setStatus(Claim.ClaimStatus.PENDING);
//
//        return claimRepository.save(claim);
//    }
//
//    @Override
//    public Claim getClaimById(Long claimId) throws ResourceNotFoundException {
//        return claimRepository.findById(claimId)
//                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + claimId));
//    }
//
//    @Override
//    public List<Claim> getClaimsByCitizen(Long citizenId) {
//        return claimRepository.findByCitizenId(citizenId);
//    }
//
//    @Override
//    @Transactional
//    public Claim updateClaimStatus(Long claimId, Claim.ClaimStatus status) throws ResourceNotFoundException {
//        Claim claim = getClaimById(claimId);
//        if (claim.getStatus() != Claim.ClaimStatus.PENDING) {
//            throw new BadRequestException("Only pending claims can be updated");
//        }
//        claim.setStatus(status);
//        Claim updated= claimRepository.save(claim);
//
//        ClaimValidation validation=new ClaimValidation();
//        validation.setClaimId(updated.getClaimId());
//        validation.setOfficerId(100l);//temporary value
//        validation.setValidationDate(java.time.LocalDate.now().toString());
//        validation.setResult(status.name());
//        validation.setRemarks("status updated via API");
//
//        validationRepository.save(validation);
//        return updated;
//    }
//
//    @Override
//    public List<ClaimValidation> getClaimsValidations(){
//        return validationRepository.findAll();
//    }
//
//
//    public Claim enrollmentFallback(Long citizenId, Claim claim,Throwable t){
//        throw  new BadRequestException("Enrollment service is currently unavailable. Cannot fetch enrollment details. Please try later.");
//    }
//
//
//}

package com.cts.claim_service.serviceImpl;

import com.cts.claim_service.client.EnrollmentClient;
import com.cts.claim_service.dto.ClaimRequestDTO;
import com.cts.claim_service.dto.ClaimResponseDTO;
import com.cts.claim_service.mapper.ClaimMapper;
import com.cts.claim_service.model.ClaimValidation;
import com.cts.claim_service.repository.ClaimValidationRepository;
import com.cts.claim_service.service.ClaimService;
import com.cts.claim_service.exception.BadRequestException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.cts.aspect.Audited;
import com.cts.claim_service.exception.ResourceNotFoundException;
import com.cts.claim_service.model.Claim;
import com.cts.claim_service.repository.ClaimRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private EnrollmentClient enrollmentClient;
    @Autowired
    private ClaimValidationRepository validationRepository;
    @Autowired
    private ClaimMapper claimMapper;

    @Override
    //@Audited(action = "CREATE", resource = "Claim")
    @CircuitBreaker(name = "enrollmentService", fallbackMethod = "enrollmentFallback")
    public ClaimResponseDTO createClaim(Long citizenId, ClaimRequestDTO dto) {
        Boolean isValid = enrollmentClient.validateEnrollment(citizenId, dto.getSchemeId());
        if (!isValid) {
            throw new BadRequestException("Citizen is not enrolled in this scheme");
        }

        Claim claim = claimMapper.toEntity(dto);
        claim.setCitizenId(citizenId);
        claim.setClaimDate(LocalDate.now().toString());
        claim.setStatus(Claim.ClaimStatus.PENDING);

        Claim saved = claimRepository.save(claim);
        return claimMapper.toDto(saved);
    }

    @Override
    public ClaimResponseDTO getClaimById(Long claimId) throws ResourceNotFoundException {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + claimId));
        return claimMapper.toDto(claim);
    }

    @Override
    public List<ClaimResponseDTO> getClaimsByCitizen(Long citizenId) {
        return claimRepository.findByCitizenId(citizenId)
                .stream()
                .map(claimMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClaimResponseDTO updateClaimStatus(Long claimId, Claim.ClaimStatus status) throws ResourceNotFoundException {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + claimId));

        if (claim.getStatus() != Claim.ClaimStatus.PENDING) {
            throw new BadRequestException("Only pending claims can be updated");
        }
        claim.setStatus(status);
        Claim updated = claimRepository.save(claim);

        ClaimValidation validation = new ClaimValidation();
        validation.setClaimId(updated.getClaimId());
        validation.setOfficerId(100l); //temporary value
        validation.setValidationDate(java.time.LocalDate.now().toString());
        validation.setResult(status.name());
        validation.setRemarks("status updated via API");

        validationRepository.save(validation);
        return claimMapper.toDto(updated);
    }

    @Override
    public List<ClaimValidation> getClaimsValidations() {
        return validationRepository.findAll();
    }

    public ClaimResponseDTO enrollmentFallback(Long citizenId, ClaimRequestDTO dto, Throwable t) {
        throw new BadRequestException("Enrollment service is currently unavailable. Cannot fetch enrollment details. Please try later.");
    }
}
