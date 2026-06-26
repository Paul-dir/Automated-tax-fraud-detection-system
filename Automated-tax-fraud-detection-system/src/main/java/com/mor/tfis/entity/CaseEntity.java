package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "case_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseEntity extends BaseEntity {
    @Column(name = "case_number", unique = true, nullable = false, length = 50)
    private String caseNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_type", nullable = false)
    private CaseType caseType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(name = "informant_id")
    private java.util.UUID informantId;

    @Column(name = "taxpayer_id")
    private java.util.UUID taxpayerId;

    @Column(name = "assigned_officer_id")
    private java.util.UUID assignedOfficerId;

    @Column(name = "assigned_team_leader_id")
    private java.util.UUID assignedTeamLeaderId;

    @Column(name = "source_channel")
    private String sourceChannel;

    private String description;
    private String notes;

    @Column(name = "registration_date")
    private java.time.LocalDateTime registrationDate;

    @Column(name = "target_completion_date")
    private LocalDate targetCompletionDate;

    @Column(name = "actual_completion_date")
    private LocalDate actualCompletionDate;

    @Column(name = "fraud_eligible")
    private Boolean fraudEligible;

    @Column(name = "closure_reason")
    private String closureReason;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    public enum CaseType { INTELLIGENCE, REFERRAL, INVESTIGATION, JOINT }
    public enum CaseStatus { REGISTERED, ASSIGNED, ANALYSIS, REPORT_PREPARATION, REVIEW, APPROVED, INVESTIGATION, CLOSED, REJECTED }
    public enum Priority { LOW, MEDIUM, HIGH, URGENT }
}