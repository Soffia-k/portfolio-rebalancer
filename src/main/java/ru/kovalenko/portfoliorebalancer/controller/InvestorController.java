package ru.kovalenko.portfoliorebalancer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kovalenko.portfoliorebalancer.domain.Investor;
import ru.kovalenko.portfoliorebalancer.service.InvestorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/investors")
public class InvestorController {

    private final InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping
    public List<Investor> getAll() {
        return investorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investor> getById(@PathVariable Long id) {
        return investorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}