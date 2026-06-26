// ChainOfCustody.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chain_of_custody")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChainOfCustody {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "evidence_id")
    private java.util.UUID evidenceId;

    @Column(name = "handler_id")
    private java.util.UUID handlerId;

    private String action;
    private String location;
    private String remarks;

    @Column(name = "handled_at")
    private LocalDateTime handledAt;
}