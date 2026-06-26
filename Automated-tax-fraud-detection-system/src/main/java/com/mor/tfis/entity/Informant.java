package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "informant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Informant extends BaseEntity {
    private String name;
    @Column(name = "contact_info")
    private String contactInfo;
    @Column(name = "tax_id")
    private String taxId;
    @Column(name = "is_anonymous")
    private Boolean isAnonymous;
    @Column(name = "preferred_channel")
    private String preferredChannel;
}