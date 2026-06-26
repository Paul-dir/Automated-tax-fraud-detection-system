package com.mor.tfis.repository;

import com.mor.tfis.entity.AssignmentRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRuleRepository extends JpaRepository<AssignmentRule, UUID> {
    List<AssignmentRule> findByIsActiveTrueOrderByPriorityAsc();
    List<AssignmentRule> findByRuleTypeAndIsActiveTrue(String ruleType);
}