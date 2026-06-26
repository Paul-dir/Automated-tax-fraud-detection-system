package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "intelligence_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntelligenceReport extends BaseEntity {
    @Column(name = "report_number", unique = true, nullable = false, length = 50)
    private String reportNumber;

    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(nullable = false)
    private String findings;

    private String conclusions;
    private String recommendations;

    @Column(name = "strength_weakness_doc")
    private String strengthWeaknessDoc;

    @Column(name = "prepared_by_id")
    private java.util.UUID preparedById;

    @Column(name = "reviewed_by_id")
    private java.util.UUID reviewedById;

    @Column(name = "approved_by_id")
    private java.util.UUID approvedById;

    @Column(name = "review_status")
    private String reviewStatus;

    @Column(name = "review_comments")
    private String reviewComments;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name = "digital_signature_hash")
    private String digitalSignatureHash;
}