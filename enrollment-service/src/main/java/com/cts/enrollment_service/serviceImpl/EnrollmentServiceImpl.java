//package com.cts.enrollment_service.serviceImpl;
//
//import com.cts.enrollment_service.service.EnrollmentService;
//
//
//import com.cts.enrollment_service.exception.BadRequestException;
//import feign.FeignException;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
////import com.cts.aspect.Audited;
//import com.cts.enrollment_service.exception.ResourceNotFoundException;
//import com.cts.enrollment_service.model.Enrollment;
//import com.cts.enrollment_service.repository.EnrollmentRepository;
//import com.cts.enrollment_service.client.SchemeClient;
//
//import jakarta.transaction.Transactional;
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//
//public class EnrollmentServiceImpl implements EnrollmentService {
//
//    @Autowired
//    private EnrollmentRepository enrollmentRepository;
//
//    @Autowired
//    private SchemeClient schemeClient;
//
//    @Override
//    //@Audited(action = "CREATE", resource = "Enrollment")
//    @CircuitBreaker(name="schemeService",fallbackMethod = "schemeFallback")
//    public Enrollment createEnrollment(Long citizenId, Enrollment enrollment) {
//
//        try {
//            Object scheme = schemeClient.getSchemeById(enrollment.getSchemeId());
//        }catch(FeignException.NotFound ex){
//
//            throw new BadRequestException("Scheme with Id "+enrollment.getSchemeId()+" doesn't exist.");
//        }
//
//        enrollment.setCitizenId(citizenId);
//        enrollment.setEnrollmentDate(LocalDate.now().toString());
//        enrollment.setStatus(Enrollment.EnrollmentStatus.PENDING);
//
//        return enrollmentRepository.save(enrollment);
//    }
//
//    @Override
//    public Enrollment getEnrollmentById(Long enrollmentId) throws ResourceNotFoundException {
//        return enrollmentRepository.findById(enrollmentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));
//    }
//
//    @Override
//    public List<Enrollment> getEnrollmentsByCitizen(Long citizenId) {
//        return enrollmentRepository.findByCitizenId(citizenId);
//    }
//
//    @Override
//    @Transactional
//    public Enrollment updateEnrollmentStatus(Long enrollmentId, Enrollment.EnrollmentStatus status)
//            throws ResourceNotFoundException {
//
//        Enrollment enrollment = getEnrollmentById(enrollmentId);
//
//        if (enrollment.getStatus() != Enrollment.EnrollmentStatus.PENDING) {
//            throw new BadRequestException("Only pending enrollments can be updated");
//        }
//
//        enrollment.setStatus(status);
//        return enrollmentRepository.save(enrollment);
//    }
//
//    @Override
//    public Boolean existsEnrollment(Long citizenId,Long schemeId){
//        return enrollmentRepository.existsByCitizenIdAndSchemeId(citizenId,schemeId);
//    }
//
//    public Enrollment schemeFallback(Long citizenId,Enrollment enrollment,Throwable t){
//        throw  new BadRequestException("Scheme service is currently unavailable. Cannot enroll for now. Please try later.");
//    }
//}
//


package com.cts.enrollment_service.serviceImpl;

import com.cts.enrollment_service.dto.EnrollmentRequestDTO;
import com.cts.enrollment_service.dto.EnrollmentResponseDTO;
import com.cts.enrollment_service.mapper.EnrollmentMapper;
import com.cts.enrollment_service.service.EnrollmentService;
import com.cts.enrollment_service.exception.BadRequestException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.cts.aspect.Audited;
import com.cts.enrollment_service.exception.ResourceNotFoundException;
import com.cts.enrollment_service.model.Enrollment;
import com.cts.enrollment_service.repository.EnrollmentRepository;
import com.cts.enrollment_service.client.SchemeClient;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private SchemeClient schemeClient;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Override
    //@Audited(action = "CREATE", resource = "Enrollment")
    @CircuitBreaker(name = "schemeService", fallbackMethod = "schemeFallback")
    public EnrollmentResponseDTO createEnrollment(Long citizenId, EnrollmentRequestDTO dto) {

        try {
            Object scheme = schemeClient.getSchemeById(dto.getSchemeId());
        } catch (FeignException.NotFound ex) {
            throw new BadRequestException("Scheme with Id " + dto.getSchemeId() + " doesn't exist.");
        }

        Enrollment enrollment = enrollmentMapper.toEntity(dto);
        enrollment.setCitizenId(citizenId);
        enrollment.setEnrollmentDate(LocalDate.now().toString());
        enrollment.setStatus(Enrollment.EnrollmentStatus.PENDING);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    @Override
    public EnrollmentResponseDTO getEnrollmentById(Long enrollmentId) throws ResourceNotFoundException {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByCitizen(Long citizenId) {
        return enrollmentRepository.findByCitizenId(citizenId)
                .stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnrollmentResponseDTO updateEnrollmentStatus(Long enrollmentId, Enrollment.EnrollmentStatus status)
            throws ResourceNotFoundException {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));

        if (enrollment.getStatus() != Enrollment.EnrollmentStatus.PENDING) {
            throw new BadRequestException("Only pending enrollments can be updated");
        }

        enrollment.setStatus(status);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    @Override
    public Boolean existsEnrollment(Long citizenId, Long schemeId) {
        return enrollmentRepository.existsByCitizenIdAndSchemeId(citizenId, schemeId);
    }

    public EnrollmentResponseDTO schemeFallback(Long citizenId, EnrollmentRequestDTO dto, Throwable t) {
        throw new BadRequestException("Scheme service is currently unavailable. Cannot enroll for now. Please try later.");
    }
}