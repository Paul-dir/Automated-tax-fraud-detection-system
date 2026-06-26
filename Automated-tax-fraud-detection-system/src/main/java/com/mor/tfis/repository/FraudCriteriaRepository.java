package com.mor.tfis.repository;

import com.mor.tfis.entity.FraudCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FraudCriteriaRepository extends JpaRepository<FraudCriteria, UUID> {
List<FraudCriteria> findByIsActiveTrueOrderByPriorityAsc();
}
