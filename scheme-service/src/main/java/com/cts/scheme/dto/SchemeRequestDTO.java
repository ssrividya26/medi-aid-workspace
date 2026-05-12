package com.cts.scheme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class SchemeRequestDTO {



        @NotBlank
        private String name;
        @NotBlank
        private String description;
        @NotBlank
        private String eligibilityCriteria;
        @NotBlank
        private String benefits;
        @NotNull
        private Double maxCoverageAmount;
        @NotBlank(message = "Status is required")
        private String status;

}
