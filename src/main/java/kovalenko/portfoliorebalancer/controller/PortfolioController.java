package kovalenko.portfoliorebalancer.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import kovalenko.portfoliorebalancer.dto.PortfolioCreateRequest;
import kovalenko.portfoliorebalancer.dto.PortfolioResponse;
import kovalenko.portfoliorebalancer.dto.PortfolioUpdateRequest;
import kovalenko.portfoliorebalancer.service.PortfolioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<PortfolioResponse> getAll() {
        return portfolioService.findAll();
    }

    @GetMapping("/{id}")
    public PortfolioResponse getById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    @GetMapping("/by-investor/{investorId}")
    public List<PortfolioResponse> byInvestor(@PathVariable Long investorId) {
        return portfolioService.findByInvestorId(investorId);
    }

    @PostMapping
    public ResponseEntity<PortfolioResponse> create(@Valid @RequestBody PortfolioCreateRequest request) {
        PortfolioResponse created = portfolioService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PatchMapping("/{id}")
    public PortfolioResponse update(@PathVariable Long id,
                                    @Valid @RequestBody PortfolioUpdateRequest request) {
        return portfolioService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        portfolioService.delete(id);
    }
}