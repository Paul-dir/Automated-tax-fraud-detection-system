// Notification.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "user_id")
    private java.util.UUID userId;

    @Column(name = "case_id")
    private java.util.UUID caseId;

    private String channel;
    private String subject;
    private String body;
    private String status;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "retry_at")
    private LocalDateTime retryAt;

    @Column(name = "retry_count")
    private Integer retryCount;
}