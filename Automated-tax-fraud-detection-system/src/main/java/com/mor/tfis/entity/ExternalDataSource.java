// ExternalDataSource.java
package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "external_data_source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDataSource extends BaseEntity {
    private String name;
    @Column(name = "endpoint_url")
    private String endpointUrl;
    @Column(name = "auth_type")
    private String authType;
    @Column(name = "credentials_encrypted")
    private String credentialsEncrypted;
    @Column(name = "timeout_ms")
    private Integer timeoutMs;
    @Column(name = "retry_count")
    private Integer retryCount;
    @Column(name = "is_active")
    private Boolean isActive;
}