// JointOperationReport.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "joint_operation_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JointOperationReport extends BaseEntity {
    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "prepared_by_team_id")
    private java.util.UUID preparedByTeamId;

    private String findings;
    private String recommendations;
    private String status;

    @Column(name = "digital_signature_hash")
    private String digitalSignatureHash;

    @Column(name = "approved_by_id")
    private java.util.UUID approvedById;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
}