// Dissemination.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dissemination")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dissemination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "intelligence_report_id")
    private java.util.UUID intelligenceReportId;

    @Column(name = "department_id")
    private java.util.UUID departmentId;

    private String method;

    @Column(name = "disseminated_at")
    private LocalDateTime disseminatedAt;

    @Column(name = "disseminated_by_id")
    private java.util.UUID disseminatedById;

    @Column(name = "watermark_info")
    private String watermarkInfo;
}