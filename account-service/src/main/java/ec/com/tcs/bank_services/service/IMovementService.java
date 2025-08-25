package ec.com.tcs.bank_services.service;

import ec.com.tcs.bank_services.dto.request.MovementRequest;
import ec.com.tcs.bank_services.dto.response.MovementReportResponse;
import ec.com.tcs.bank_services.dto.response.MovementResponse;
import ec.com.tcs.bank_services.model.MovementEntity;

import java.time.LocalDate;
import java.util.List;

public interface IMovementService {

    MovementResponse createMovement(MovementRequest movement);

    MovementEntity getMovement(Long id);

    MovementResponse updateMovement(Long id, MovementRequest movement);

    Boolean deleteMovement(Long id);

    List<MovementReportResponse> generateReport(LocalDate fechaInicio, LocalDate fechaFin, String identification);
}
