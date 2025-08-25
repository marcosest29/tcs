package ec.com.tcs.bank_services.controller;

import ec.com.tcs.bank_services.dto.response.MovementReportResponse;
import ec.com.tcs.bank_services.service.IMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private IMovementService movementService;

    @GetMapping
    public ResponseEntity<List<MovementReportResponse>> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String identification) {

        List<MovementReportResponse> reports = movementService.generateReport(initialDate, endDate, identification);
        return ResponseEntity.ok(reports);
    }
}
