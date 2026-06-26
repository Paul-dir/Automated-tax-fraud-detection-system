package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "referral")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Referral extends BaseEntity {
    @Column(name = "referral_number", unique = true, nullable = false, length = 50)
    private String referralNumber;

    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "informant_id")
    private java.util.UUID informantId;

    @Column(name = "submission_channel", nullable = false)
    private String submissionChannel;

    @Column(name = "allegation_summary", nullable = false)
    private String allegationSummary;

    private String details;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> attachmentsMetadata;

    @Column(name = "evaluation_status")
    private String evaluationStatus;

    @Column(name = "evaluated_by_id")
    private java.util.UUID evaluatedById;

    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;

    @Column(name = "evaluation_notes")
    private String evaluationNotes;

    private String recommendation;

    @Column(name = "approved_by_id")
    private java.util.UUID approvedById;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name = "approval_justification")
    private String approvalJustification;
}