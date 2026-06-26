package com.mor.tfis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterCaseRequest {
    @NotBlank(message = "Case type is required")
    private String caseType;  // INTELLIGENCE, REFERRAL, INVESTIGATION, JOINT

    @NotBlank(message = "Priority is required")
    private String priority;  // LOW, MEDIUM, HIGH, URGENT

    private String informantId;  // optional, UUID as string

    private String taxpayerId;   // optional, UUID as string

    @NotBlank(message = "Source channel is required")
    private String sourceChannel;  // SMS, EMAIL, PORTAL, PHONE, INTERNAL_ITAS, SOCIAL_MEDIA

    @NotBlank(message = "Description is required")
    private String description;

    private String notes;
}