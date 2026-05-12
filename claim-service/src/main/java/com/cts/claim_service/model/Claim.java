package com.cts.claim_service.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import com.cts.claim_service.validation.ValidClaimAmount;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @NotNull(message = "Citizen ID is required")
    private Long citizenId;

    @NotNull(message = "Scheme ID is required")
    private Long schemeId;

    @NotNull(message = "Claim amount is required")
    //@ValidClaimAmount
    private Double claimAmount;

    private String claimDate;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status = ClaimStatus.PENDING;

    public enum ClaimStatus {
        PENDING, APPROVED, REJECTED
    }



}