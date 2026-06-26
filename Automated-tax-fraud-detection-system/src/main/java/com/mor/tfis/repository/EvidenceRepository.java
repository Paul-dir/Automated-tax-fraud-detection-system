package com.mor.tfis.repository;

import com.mor.tfis.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, UUID> {
long countByCaseId(UUID caseId);
}
