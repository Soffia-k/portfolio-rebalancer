package ru.kovalenko.portfoliorebalancer.service;

import org.springframework.stereotype.Service;
import ru.kovalenko.portfoliorebalancer.domain.Portfolio;
import ru.kovalenko.portfoliorebalancer.repository.PortfolioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public Optional<Portfolio> findById(Long id) {
        return portfolioRepository.findById(id);
    }

    public List<Portfolio> findByInvestorId(Long investorId) {
        return portfolioRepository.findByInvestorId(investorId);
    }
}