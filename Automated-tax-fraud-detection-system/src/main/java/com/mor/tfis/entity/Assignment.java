package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "assignable_type")
    private String assignableType;  // CASE, TASK, REPORT

    @Column(name = "assignable_id")
    private UUID assignableId;

    @Column(name = "assigned_to_id")
    private UUID assignedToId;

    @Column(name = "assigned_by_id")
    private UUID assignedById;

    @Column(name = "assignment_type")
    private String assignmentType;  // AUTOMATIC, MANUAL

    @Column(name = "rule_used")
    private String ruleUsed;

    @Column(name = "justification")
    private String justification;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    private String status;  // ACTIVE, COMPLETED, REASSIGNED
}