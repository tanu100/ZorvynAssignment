package com.zorvyn.Modules.Dashboard.Controller;

import com.zorvyn.Modules.Dashboard.Dtos.DashboardResponse;
import com.zorvyn.Modules.Dashboard.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(service.getDashboard());
    }
}