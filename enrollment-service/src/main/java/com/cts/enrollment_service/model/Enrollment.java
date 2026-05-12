package com.cts.enrollment_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @NotNull(message = "Citizen ID is required")
    private Long citizenId;

    @NotNull(message = "Scheme ID is required")
    private Long schemeId;

    private String enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.PENDING;

    public enum EnrollmentStatus {
        PENDING, APPROVED, REJECTED, ACTIVE
    }

}
