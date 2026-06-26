// ExternalQueryLog.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "external_query_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalQueryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "data_source_id")
    private java.util.UUID dataSourceId;

    @Column(name = "user_id")
    private java.util.UUID userId;

    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "query_params")
    private String queryParams;

    @Column(name = "response_status")
    private String responseStatus;

    @Column(name = "response_time_ms")
    private Integer responseTimeMs;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "queried_at")
    private LocalDateTime queriedAt;
}