package com.mor.tfis.controller;

import com.mor.tfis.entity.Assignment;
import com.mor.tfis.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/case/{caseId}/auto")
    public ResponseEntity<Assignment> autoAssignCase(
            @PathVariable UUID caseId,
            @RequestParam UUID assignedById) {
        Assignment assignment = assignmentService.autoAssignCase(caseId, assignedById);
        return ResponseEntity.ok(assignment);
    }
}