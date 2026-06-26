package com.mor.tfis.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CaseResponse {
    private UUID id;
    private String caseNumber;
    private String caseType;
    private String status;
    private String priority;
    private UUID informantId;
    private UUID taxpayerId;
    private String sourceChannel;
    private String description;
    private String notes;
    private LocalDateTime registrationDate;
    private Boolean fraudEligible;
}