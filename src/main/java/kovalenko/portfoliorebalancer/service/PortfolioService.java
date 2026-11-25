package kovalenko.portfoliorebalancer.service;

import org.springframework.stereotype.Service;
import kovalenko.portfoliorebalancer.domain.Portfolio;
import kovalenko.portfoliorebalancer.dto.PortfolioCreateRequest;
import kovalenko.portfoliorebalancer.dto.PortfolioResponse;
import kovalenko.portfoliorebalancer.dto.PortfolioUpdateRequest;
import kovalenko.portfoliorebalancer.exception.BusinessRuleException;
import kovalenko.portfoliorebalancer.exception.NotFoundException;
import kovalenko.portfoliorebalancer.repository.InvestorRepository;
import kovalenko.portfoliorebalancer.repository.PortfolioRepository;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final InvestorRepository investorRepository;

    public PortfolioService(PortfolioRepository portfolioRepository,
                            InvestorRepository investorRepository) {
        this.portfolioRepository = portfolioRepository;
        this.investorRepository = investorRepository;
    }

    public List<PortfolioResponse> findAll() {
        return portfolioRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PortfolioResponse> findByInvestorId(Long investorId) {
        if (!investorRepository.existsById(investorId)) {
            throw new NotFoundException("Investor " + investorId + " not found");
        }
        return portfolioRepository.findByInvestorId(investorId).stream()
                .map(this::toResponse)
                .toList();
    }

    public PortfolioResponse findById(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Portfolio " + id + " not found"));
        return toResponse(portfolio);
    }

    public PortfolioResponse create(PortfolioCreateRequest request) {
        if (!investorRepository.existsById(request.investorId())) {
            throw new BusinessRuleException(
                    "Cannot create portfolio for non-existent investor " + request.investorId());
        }
        Portfolio portfolio = new Portfolio(null, request.investorId(), request.name(), request.toleranceBand());
        Portfolio saved = portfolioRepository.save(portfolio);
        return toResponse(saved);
    }

    public PortfolioResponse update(Long id, PortfolioUpdateRequest request) {
        Portfolio existing = portfolioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Portfolio " + id + " not found"));

        if (request.name() != null) {
            existing.setName(request.name());
        }
        if (request.toleranceBand() != null) {
            existing.setToleranceBand(request.toleranceBand());
        }

        Portfolio updated = portfolioRepository.update(id, existing)
                .orElseThrow(() -> new NotFoundException("Portfolio " + id + " not found"));
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new NotFoundException("Portfolio " + id + " not found");
        }
        portfolioRepository.deleteById(id);
    }

    private PortfolioResponse toResponse(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getId(),
                portfolio.getInvestorId(),
                portfolio.getName(),
                portfolio.getToleranceBand()
        );
    }
}