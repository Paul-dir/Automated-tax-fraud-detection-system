package com.mor.tfis.repository;

import com.mor.tfis.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findByAssignedToIdAndStatus(UUID userId, String status);

    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.assignedToId = :userId AND a.status = 'ACTIVE'")
    long countActiveByUserId(@Param("userId") UUID userId);
}