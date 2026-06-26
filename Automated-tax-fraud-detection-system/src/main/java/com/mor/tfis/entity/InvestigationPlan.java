// InvestigationPlan.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "investigation_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestigationPlan extends BaseEntity {
    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "prepared_by_id")
    private java.util.UUID preparedById;

    private String objectives;
    private String scope;
    private String methodology;
    private String resources;
    private String timeline;

    @Column(name = "risk_assessment")
    private String riskAssessment;

    private String status;

    @Column(name = "approved_by_id")
    private java.util.UUID approvedById;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    private Integer version;
}