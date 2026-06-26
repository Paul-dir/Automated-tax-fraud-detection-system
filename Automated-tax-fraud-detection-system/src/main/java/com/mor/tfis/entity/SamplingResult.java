// SamplingResult.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "sampling_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingResult extends BaseEntity {
    @Column(name = "investigation_plan_id")
    private java.util.UUID investigationPlanId;

    @Column(name = "sampling_config_id")
    private java.util.UUID samplingConfigId;

    @Column(name = "data_category")
    private String dataCategory;

    @Column(name = "population_size")
    private Integer populationSize;

    @Column(name = "sample_size")
    private Integer sampleSize;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sample_items", columnDefinition = "jsonb")
    private Map<String, Object> sampleItems;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "test_results", columnDefinition = "jsonb")
    private Map<String, Object> testResults;

    @Column(name = "anomalies_count")
    private Integer anomaliesCount;

    @Column(name = "material_discrepancy_found")
    private Boolean materialDiscrepancyFound;

    @Column(name = "escalated_to_id")
    private java.util.UUID escalatedToId;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}