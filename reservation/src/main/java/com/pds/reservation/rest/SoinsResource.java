package com.pds.reservation.rest;

import com.pds.reservation.model.SoinsDTO;
import com.pds.reservation.service.SoinsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/soins", produces = MediaType.APPLICATION_JSON_VALUE)
public class SoinsResource {

    private final SoinsService soinsService;

    public SoinsResource(final SoinsService soinsService) {
        this.soinsService = soinsService;
    }

    @GetMapping
    public ResponseEntity<List<SoinsDTO>> getAllSoinss() {
        return ResponseEntity.ok(soinsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoinsDTO> getSoins(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(soinsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSoins(@RequestBody @Valid final SoinsDTO soinsDTO) {
        final Long createdId = soinsService.create(soinsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSoins(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SoinsDTO soinsDTO) {
        soinsService.update(id, soinsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSoins(@PathVariable(name = "id") final Long id) {
        soinsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
