package ec.com.tcs.bank_services.controller;

import ec.com.tcs.bank_services.dto.request.MovementRequest;
import ec.com.tcs.bank_services.dto.response.MovementResponse;
import ec.com.tcs.bank_services.model.MovementEntity;
import ec.com.tcs.bank_services.service.IMovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movements")
public class MovementController {

    @Autowired
    private IMovementService movementService;

    @GetMapping("/{id}")
    public ResponseEntity<MovementEntity> getMovement(@PathVariable Long id) {
        MovementEntity movement = movementService.getMovement(id);
        if(ObjectUtils.isEmpty(movement)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movement);
    }

    @PostMapping
    public ResponseEntity<MovementResponse> createMovement(@RequestBody @Valid MovementRequest movement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.createMovement(movement));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovementResponse> updateMovement(@PathVariable Long id, @RequestBody @Valid MovementRequest movement) {
        return ResponseEntity.ok(movementService.updateMovement(id,movement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        Boolean delete = movementService.deleteMovement(id);
        if(!ObjectUtils.isEmpty(delete) && delete){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
