package com.mor.tfis.service;

import com.mor.tfis.dto.RegisterCaseRequest;
import com.mor.tfis.dto.CaseResponse;
import com.mor.tfis.entity.CaseEntity;
import com.mor.tfis.repository.CaseRepository;
import com.mor.tfis.repository.InformantRepository;
import com.mor.tfis.repository.TaxpayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseService {

    private final CaseRepository caseRepository;
    private final InformantRepository informantRepository;
    private final TaxpayerRepository taxpayerRepository;

    @Transactional
    public CaseResponse registerSuspectedCase(RegisterCaseRequest request) {
        // Generate unique case number: TFIS-YYYY-XXXXX
        String caseNumber = generateCaseNumber();

        // Validate and convert optional UUIDs
        UUID informantId = parseUuid(request.getInformantId());
        UUID taxpayerId = parseUuid(request.getTaxpayerId());

        // Optional: validate that informant and taxpayer exist if provided
        if (informantId != null && !informantRepository.existsById(informantId)) {
            throw new IllegalArgumentException("Informant not found with id: " + informantId);
        }
        if (taxpayerId != null && !taxpayerRepository.existsById(taxpayerId)) {
            throw new IllegalArgumentException("Taxpayer not found with id: " + taxpayerId);
        }

        // Build entity
        CaseEntity newCase = CaseEntity.builder()
                .caseNumber(caseNumber)
                .caseType(CaseEntity.CaseType.valueOf(request.getCaseType()))
                .status(CaseEntity.CaseStatus.REGISTERED)
                .priority(CaseEntity.Priority.valueOf(request.getPriority()))
                .informantId(informantId)
                .taxpayerId(taxpayerId)
                .sourceChannel(request.getSourceChannel())
                .description(request.getDescription())
                .notes(request.getNotes())
                .fraudEligible(false)
                .registrationDate(LocalDateTime.now())
                .build();

        CaseEntity saved = caseRepository.save(newCase);
        log.info("Registered new case: {} with ID: {}", caseNumber, saved.getId());

        return mapToResponse(saved);
    }

    private String generateCaseNumber() {
        String year = String.valueOf(LocalDateTime.now().getYear());
        long count = caseRepository.count() + 1;
        return String.format("TFIS-%s-%05d", year, count);
    }

    private UUID parseUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.isBlank()) {
            return null;
        }
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + uuidStr);
        }
    }

    private CaseResponse mapToResponse(CaseEntity entity) {
        return CaseResponse.builder()
                .id(entity.getId())
                .caseNumber(entity.getCaseNumber())
                .caseType(entity.getCaseType().name())
                .status(entity.getStatus().name())
                .priority(entity.getPriority().name())
                .informantId(entity.getInformantId())
                .taxpayerId(entity.getTaxpayerId())
                .sourceChannel(entity.getSourceChannel())
                .description(entity.getDescription())
                .notes(entity.getNotes())
                .registrationDate(entity.getRegistrationDate())
                .fraudEligible(entity.getFraudEligible())
                .build();
    }
}