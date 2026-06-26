// InvestigationReport.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "investigation_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestigationReport extends BaseEntity {
    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "prepared_by_id")
    private java.util.UUID preparedById;

    @Column(name = "reviewed_by_id")
    private java.util.UUID reviewedById;

    @Column(name = "approved_by_id")
    private java.util.UUID approvedById;

    private String findings;
    private String conclusions;
    private String recommendations;

    @Column(name = "penalty_calculation")
    private String penaltyCalculation;

    @Column(name = "report_status")
    private String reportStatus;

    @Column(name = "review_comments")
    private String reviewComments;

    @Column(name = "exit_conference_date")
    private LocalDateTime exitConferenceDate;

    @Column(name = "exit_conference_minutes")
    private String exitConferenceMinutes;

    @Column(name = "digital_signature_hash")
    private String digitalSignatureHash;
}