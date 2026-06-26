package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "assignment_rule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentRule extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ruleType;  // ROLE, CASE_TYPE, JURISDICTION, EXPERTISE, WORKLOAD

    @Column(name = "rule_value")
    private String ruleValue;  // e.g., "INTELLIGENCE_OFFICER", "HIGH_RISK", "ADDIS_ABABA"

    @Column(name = "priority")
    private Integer priority;  // lower number = higher priority

    @Column(name = "is_active")
    private Boolean isActive;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> parameters;  // e.g., max caseload, required skills

    @Column(name = "description")
    private String description;
}