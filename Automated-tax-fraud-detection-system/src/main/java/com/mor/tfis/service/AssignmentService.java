package com.mor.tfis.service;

import com.mor.tfis.entity.*;
import com.mor.tfis.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentRuleRepository ruleRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;

    @Transactional
    public Assignment autoAssignCase(UUID caseId, UUID assignedById) {
        CaseEntity caseEntity = caseRepository.findById(caseId)
                .orElseThrow(() -> new IllegalArgumentException("Case not found: " + caseId));

        // Determine required role based on case type
        String requiredRole = mapCaseTypeToRole(caseEntity.getCaseType());

        // Find eligible users (active, with required role, not overloaded)
        List<User> eligibleUsers = findEligibleUsers(requiredRole, caseEntity);

        if (eligibleUsers.isEmpty()) {
            throw new IllegalStateException("No eligible users found for assignment. Case: " + caseId);
        }

        // Apply assignment rules to select best candidate
        User selectedUser = selectBestCandidate(eligibleUsers, caseEntity);

        // Create assignment record
        Assignment assignment = Assignment.builder()
                .assignableType("CASE")
                .assignableId(caseId)
                .assignedToId(selectedUser.getId())
                .assignedById(assignedById)
                .assignmentType("AUTOMATIC")
                .ruleUsed("WORKLOAD_BALANCING + ROLE_MATCH")
                .assignedAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        Assignment saved = assignmentRepository.save(assignment);

        // Update case with assigned officer
        caseEntity.setAssignedOfficerId(selectedUser.getId());
        caseRepository.save(caseEntity);

        log.info("Auto-assigned case {} to user {} via rule engine", caseId, selectedUser.getUsername());
        return saved;
    }

    private String mapCaseTypeToRole(CaseEntity.CaseType caseType) {
        return switch (caseType) {
            case INTELLIGENCE -> "INTELLIGENCE_OFFICER";
            case REFERRAL -> "INVESTIGATION_OFFICER";
            case INVESTIGATION -> "INVESTIGATION_OFFICER";
            case JOINT -> "TEAM_LEADER";
        };
    }

    private List<User> findEligibleUsers(String requiredRole, CaseEntity caseEntity) {
        // Get all users with required role
        List<User> allUsers = userRepository.findAll();
        List<User> eligible = allUsers.stream()
                .filter(user -> user.getRole().name().equals(requiredRole))
                .filter(user -> {
                    long activeCases = assignmentRepository.countActiveByUserId(user.getId());
                    // Max 5 active cases per officer (configurable)
                    return activeCases < 5;
                })
                .collect(Collectors.toList());

        // Apply jurisdiction/expertise rules (simplified for now)
        // In a real system, you'd check department/region
        return eligible;
    }

    private User selectBestCandidate(List<User> candidates, CaseEntity caseEntity) {
        // Simple workload balancing: choose user with fewest active cases
        return candidates.stream()
                .min(Comparator.comparingLong(user -> assignmentRepository.countActiveByUserId(user.getId())))
                .orElse(candidates.get(0));
    }

    public long getActiveCaseCountForUser(UUID userId) {
        return assignmentRepository.countActiveByUserId(userId);
    }
}