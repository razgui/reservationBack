package com.pds.reservation.rest;

import com.pds.reservation.model.PatientDTO;
import com.pds.reservation.service.PatientService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientResource {

    private final PatientService patientService;

    public PatientResource(final PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(patientService.get(id));
    }

    @GetMapping("/number")
    public ResponseEntity<Integer> getPatient() {
        return ResponseEntity.ok(patientService.getNbreOfPatients());
    }

    @GetMapping("/PerMonth")
    public ResponseEntity<List<Integer>> getReservationPerMonth() {
        return ResponseEntity.ok(patientService.getNumberOfPatientAddPerMonth());
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPatient(@RequestBody @Valid final PatientDTO patientDTO) {
        final Long createdId = patientService.create(patientDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePatient(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PatientDTO patientDTO) {
        patientService.update(id, patientDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePatient(@PathVariable(name = "id") final Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
