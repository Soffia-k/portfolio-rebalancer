package kovalenko.portfoliorebalancer.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import kovalenko.portfoliorebalancer.dto.InvestorCreateRequest;
import kovalenko.portfoliorebalancer.dto.InvestorResponse;
import kovalenko.portfoliorebalancer.dto.InvestorUpdateRequest;
import kovalenko.portfoliorebalancer.service.InvestorService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/investors")
public class InvestorController {

    private final InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping
    public List<InvestorResponse> getAll() {
        return investorService.findAll();
    }

    @GetMapping("/{id}")
    public InvestorResponse getById(@PathVariable Long id) {
        return investorService.findById(id);
    }

    @PostMapping
    public ResponseEntity<InvestorResponse> create(@Valid @RequestBody InvestorCreateRequest request) {
        InvestorResponse created = investorService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PatchMapping("/{id}")
    public InvestorResponse update(@PathVariable Long id,
                                   @Valid @RequestBody InvestorUpdateRequest request) {
        return investorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        investorService.delete(id);
    }
}