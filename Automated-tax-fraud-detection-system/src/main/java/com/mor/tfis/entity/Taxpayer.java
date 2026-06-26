package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taxpayer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taxpayer extends BaseEntity {
    @Column(unique = true, nullable = false, length = 20)
    private String tin;
    @Column(nullable = false)
    private String name;
    @Column(name = "registration_number")
    private String registrationNumber;
    @Column(name = "business_type")
    private String businessType;
    private String address;
    @Column(name = "contact_person")
    private String contactPerson;
    private String phone;
    private String email;
    @Column(name = "risk_score")
    private Integer riskScore;
}