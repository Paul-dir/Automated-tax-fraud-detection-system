// CaseRepository.java
package com.mor.tfis.repository;

import com.mor.tfis.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<CaseEntity, UUID> {
    Optional<CaseEntity> findByCaseNumber(String caseNumber);
    boolean existsByCaseNumber(String caseNumber);
}