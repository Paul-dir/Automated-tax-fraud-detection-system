package com.mor.tfis.controller;

import com.mor.tfis.dto.RegisterCaseRequest;
import com.mor.tfis.dto.CaseResponse;
import com.mor.tfis.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PostMapping("/register")
    public ResponseEntity<CaseResponse> registerCase(@Valid @RequestBody RegisterCaseRequest request) {
        CaseResponse response = caseService.registerSuspectedCase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}