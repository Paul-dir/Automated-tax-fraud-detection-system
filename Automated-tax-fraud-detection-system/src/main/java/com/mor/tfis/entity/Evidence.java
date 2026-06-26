// Evidence.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evidence {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "case_id")
    private java.util.UUID caseId;

    @Column(name = "referral_id")
    private java.util.UUID referralId;

    @Column(name = "evidence_type", nullable = false)
    private String evidenceType;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_hash", nullable = false)
    private String fileHash;

    @Column(name = "file_size_bytes")
    private Integer fileSizeBytes;

    @Column(name = "mime_type")
    private String mimeType;

    private String description;
    private String source;

    @Column(name = "chain_of_custody_id")
    private java.util.UUID chainOfCustodyId;

    private String status;

    @Column(name = "uploaded_by_id")
    private java.util.UUID uploadedById;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}

