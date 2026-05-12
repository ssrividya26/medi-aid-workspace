package com.cts.scheme.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Scheme")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schemeId;

    @NotBlank(message = "Scheme name is required")
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String eligibilityCriteria;

    @Column(length = 500)
    private String benefits;

    private Double maxCoverageAmount;

    @Column(nullable = false)
    private String status = "ACTIVE";

}