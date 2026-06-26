package com.mor.tfis.service;

import com.mor.tfis.entity.CaseEntity;
import com.mor.tfis.entity.FraudCriteria;
import com.mor.tfis.entity.Taxpayer;
import com.mor.tfis.repository.CaseRepository;
import com.mor.tfis.repository.EvidenceRepository;
import com.mor.tfis.repository.FraudCriteriaRepository;
import com.mor.tfis.repository.TaxpayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudEvaluationService {

    private final FraudCriteriaRepository criteriaRepository;
    private final CaseRepository caseRepository;
    private final TaxpayerRepository taxpayerRepository;  // <-- new dependency
    private final EvidenceRepository evidenceRepository;

    @Transactional
    public boolean evaluateCase(UUID caseId) {
        CaseEntity caseEntity = caseRepository.findById(caseId)
                .orElseThrow(() -> new IllegalArgumentException("Case not found: " + caseId));

        List<FraudCriteria> activeCriteria = criteriaRepository.findByIsActiveTrueOrderByPriorityAsc();

        boolean fraudDetected = false;
        for (FraudCriteria criteria : activeCriteria) {
            if (evaluateCriteria(criteria, caseEntity)) {
                fraudDetected = true;
                log.info("Fraud criteria '{}' matched for case {}", criteria.getName(), caseId);
                break;
            }
        }

        caseEntity.setFraudEligible(fraudDetected);
        caseRepository.save(caseEntity);

        return fraudDetected;
    }

    private boolean evaluateCriteria(FraudCriteria criteria, CaseEntity caseEntity) {
        String ruleType = criteria.getRuleType();
        Map<String, Object> params = criteria.getParameters();

        return switch (ruleType) {
            case "CASE_PRIORITY" -> evaluatePriority(caseEntity, criteria.getRuleValue());
            case "TAXPAYER_RISK" -> evaluateTaxpayerRisk(caseEntity, params);
            case "EVIDENCE_COUNT" -> evaluateEvidenceCount(caseEntity, params);
            default -> {
                log.warn("Unknown rule type: {}", ruleType);
                yield false;
            }
        };
    }

    private boolean evaluatePriority(CaseEntity caseEntity, String requiredPriority) {
        return caseEntity.getPriority().name().equals(requiredPriority);
    }

    private boolean evaluateTaxpayerRisk(CaseEntity caseEntity, Map<String, Object> params) {
        if (caseEntity.getTaxpayerId() == null) return false;
        Taxpayer taxpayer = taxpayerRepository.findById(caseEntity.getTaxpayerId()).orElse(null);
        if (taxpayer == null) return false;
        int riskScore = taxpayer.getRiskScore() != null ? taxpayer.getRiskScore() : 0;
        int threshold = params != null && params.containsKey("min_risk") ? (int) params.get("min_risk") : 80;
        return riskScore >= threshold;
    }

    private boolean evaluateEvidenceCount(CaseEntity caseEntity, Map<String, Object> params) {
        // This would require counting evidence items for this case.
        // We'll assume there is an evidence repository. For now, return false as placeholder.
        // In real implementation: evidenceRepository.countByCaseId(caseEntity.getId()) >= threshold
        log.debug("Evidence count evaluation not yet implemented");
        return false;
    }

    private boolean evaluateTaxGap(CaseEntity caseEntity, Map<String, Object> params) {
        // This would require fetching financial data from ITAS or case metadata.
        // Placeholder: assume tax gap is stored in case metadata or computed.
        if (caseEntity.getMetadata() != null && caseEntity.getMetadata().containsKey("estimatedTaxGap")) {
            Number gap = (Number) caseEntity.getMetadata().get("estimatedTaxGap");
            int threshold = params != null && params.containsKey("min_gap") ? (int) params.get("min_gap") : 500000;
            return gap.doubleValue() >= threshold;
        }
        return false;
    }

    private boolean evaluateInformantCredibility(CaseEntity caseEntity, Map<String, Object> params) {
        // If informant is anonymous, consider less credible? Or if informant has prior valid reports.
        // For simplicity, we'll return false for now.
        return false;
    }
}