package kovalenko.portfoliorebalancer.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import kovalenko.portfoliorebalancer.domain.Instrument;
import kovalenko.portfoliorebalancer.dto.InstrumentCreateRequest;
import kovalenko.portfoliorebalancer.dto.InstrumentResponse;
import kovalenko.portfoliorebalancer.dto.InstrumentUpdateRequest;
import kovalenko.portfoliorebalancer.service.InstrumentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/instruments")
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public List<InstrumentResponse> getAll() {
        return instrumentService.findAll();
    }

    @GetMapping("/{id}")
    public InstrumentResponse getById(@PathVariable Long id) {
        return instrumentService.findById(id);
    }

    @GetMapping("/by-type/{type}")
    public List<InstrumentResponse> byType(@PathVariable Instrument.Type type) {
        return instrumentService.findByType(type);
    }

    @PostMapping
    public ResponseEntity<InstrumentResponse> create(@Valid @RequestBody InstrumentCreateRequest request) {
        InstrumentResponse created = instrumentService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PatchMapping("/{id}")
    public InstrumentResponse update(@PathVariable Long id,
                                     @Valid @RequestBody InstrumentUpdateRequest request) {
        return instrumentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        instrumentService.delete(id);
    }
}